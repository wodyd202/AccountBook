package com.payhere.accountbook.services.customer.infrastructure;

import com.payhere.accountbook.services.customer.application.CustomerRecord;
import com.payhere.accountbook.services.customer.application.CustomerSearchRepository;
import com.payhere.accountbook.services.customer.domain.Customer;
import com.payhere.accountbook.services.customer.domain.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InmemoryCustomerSearchRepository implements CustomerSearchRepository {
    private final CustomerRepository customerRepository;

    @Override
    public Optional<CustomerRecord> findByEmail(String email) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if(optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            return Optional.of(CustomerRecord.mapFrom(customer));
        }
        return Optional.empty();
    }
}
