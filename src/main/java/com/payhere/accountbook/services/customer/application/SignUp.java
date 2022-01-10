package com.payhere.accountbook.services.customer.application;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUp {
    private String email;
    private String password;

    public static SignUp of(String email, String password) {
        return new SignUp(email, password);
    }
}
