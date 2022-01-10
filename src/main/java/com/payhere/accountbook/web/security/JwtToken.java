package com.payhere.accountbook.web.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtToken {
    private String value;

    public static JwtToken of(String token) {
        return new JwtToken(token);
    }

    public String get() {
        return value;
    }
}
