package com.payhere.accountbook.web.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {
    private final JwtTokenResolver jwtTokenResolver;
    private final JwtTokenValidator jwtTokenValidator;
    private final JwtAuthenticationProvider authenticationProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            JwtToken jwtToken = jwtTokenResolver.resolve(servletRequest).orElseThrow(NoHasPermissionException::new);
            if(jwtTokenValidator.isInvalid(jwtToken)){
                setExpireTokenResponse(servletResponse);
                return;
            }
            Authentication authentication = authenticationProvider.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (NoHasPermissionException e) {
        } catch (UsernameNotFoundException e){
            setUsernameNotFoundResponse(servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setExpireTokenResponse(ServletResponse servletResponse) throws IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("utf-8");
        response.getWriter().println("토큰 정보가 잘못되었거나 혹은 토큰이 만료되었습니다.");
        response.setStatus(401);
    }

    private void setUsernameNotFoundResponse(ServletResponse servletResponse) throws IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("utf-8");
        response.getWriter().println("해당 고객이 존재하지 않습니다.");
        response.setStatus(401);
    }
}
