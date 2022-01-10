package com.payhere.accountbook.web.security;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final JwtProperties jwtProperties;

    public Authentication getAuthentication(JwtToken jwtToken) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getIdentifier(jwtToken.get()));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getIdentifier(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody().getSubject();
    }
}
