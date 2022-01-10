package com.payhere.accountbook.web.security;

import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class JwtTokenResolver {

    private final static String AUTHENTICATION = "Authentication";
    public Optional<JwtToken> resolve(ServletRequest servletRequest) {
        String token = ((HttpServletRequest) servletRequest).getHeader(AUTHENTICATION);
        if(isInvalidToken(token)){
            return Optional.empty();
        }
        return Optional.of(JwtToken.of(token));
    }

    private boolean isInvalidToken(String header) {
        return header == null || (header != null && header.isEmpty());
    }
}
