package com.payhere.accountbook.services.customer.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.payhere.accountbook.services.customer.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerRecord {
    private String email;
    @JsonIgnore
    private String password;

    public static CustomerRecord mapFrom(Customer customer) {
        return new CustomerRecord(customer.getEmail(), customer.getPassword());
    }
}
