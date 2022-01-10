package com.payhere.accountbook.services.customer.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {
    @Id
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
