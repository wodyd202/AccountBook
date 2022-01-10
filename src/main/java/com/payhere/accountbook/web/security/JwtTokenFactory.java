package com.payhere.accountbook.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class JwtTokenFactory {
    private final JwtProperties jwtProperties;

    public JwtToken createToken(UserDetails userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("roles", userDetails.getAuthorities().stream().map(c->c.getAuthority()).collect(toList()));
        Date now = new Date();
        return JwtToken.of(Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getExpire()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact());
    }
}
