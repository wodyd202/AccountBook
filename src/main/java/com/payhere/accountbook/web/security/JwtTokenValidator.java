package com.payhere.accountbook.web.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenValidator {
    private final JwtProperties jwtProperties;

    public boolean isInvalid(JwtToken jwtToken) {
        try{
            Jws<Claims> jwt = Jwts.parser().setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(jwtToken.get());
            return jwt.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            e.printStackTrace();
            return true;
        }
    }
}
