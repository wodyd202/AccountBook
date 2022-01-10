package com.payhere.accountbook.web.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secretKey;
    private long expire;

    @PostConstruct
    void setUp(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
}
