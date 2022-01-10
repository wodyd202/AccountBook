package com.payhere.accountbook.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
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
            Jwt<Header, Claims> jwt = Jwts.parser().setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJwt(jwtToken.get());
            return jwt.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return true;
        }
    }
}
