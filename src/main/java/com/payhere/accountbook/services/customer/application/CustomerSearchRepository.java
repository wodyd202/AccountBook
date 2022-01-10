package com.payhere.accountbook.services.customer.application;

import java.util.Optional;

public interface CustomerSearchRepository {
    Optional<CustomerRecord> findByEmail(String email);
}
