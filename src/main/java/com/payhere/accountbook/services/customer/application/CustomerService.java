package com.payhere.accountbook.services.customer.application;

import com.payhere.accountbook.services.customer.domain.Customer;
import com.payhere.accountbook.services.customer.domain.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CustomerService implements UserDetailsService {
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

    private final CustomerSearchRepository customerSearchRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomerRecord customerRecord = customerSearchRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(email));
        return new User(customerRecord.getEmail(), customerRecord.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
    }
}
