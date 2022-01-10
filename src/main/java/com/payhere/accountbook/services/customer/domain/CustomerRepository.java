package com.payhere.accountbook.services.customer.domain;

public interface CustomerRepository {
    void save(Customer customer);
    boolean existByEmail(String email);
}
