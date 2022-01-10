package com.payhere.accountbook.services.customer.domain;

import java.util.Optional;

public interface CustomerRepository {
    void save(Customer customer);
    boolean existByEmail(String email);
    Optional<Customer> findByEmail(String email);
}
