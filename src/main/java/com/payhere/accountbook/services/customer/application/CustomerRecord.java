package com.payhere.accountbook.services.customer.application;

import com.payhere.accountbook.services.customer.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerRecord {
    private String email;
    private String password;

    public static CustomerRecord mapFrom(Customer customer) {
        return new CustomerRecord(customer.getEmail(), customer.getPassword());
    }
}
