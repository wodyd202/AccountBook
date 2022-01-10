package com.payhere.accountbook.services.customer.domain;

import lombok.Getter;

@Getter
public class Customer {
    private String email;
    private String password;

    private Customer(String email, String password){
        this.email = email;
        this.password = password;
    }

    public static Customer of(String email, String password) {
        return new Customer(email, password);
    }
}
