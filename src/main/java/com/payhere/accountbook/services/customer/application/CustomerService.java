package com.payhere.accountbook.services.customer.application;

import com.payhere.accountbook.services.customer.application.CustomerRecord;
import com.payhere.accountbook.services.customer.domain.Customer;
import com.payhere.accountbook.services.customer.domain.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerRecord signUp(SignUp signUp) {
        verifyNotExistCustomer(signUp.getEmail());

        Customer customer = Customer.of(signUp.getEmail(), signUp.getPassword());
        customerRepository.save(customer);

        return CustomerRecord.mapFrom(customer);
    }

    private void verifyNotExistCustomer(String email) {
        if(customerRepository.existByEmail(email)){
            throw new DuplicateCustomerEmailException();
        }
    }
}
