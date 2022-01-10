package com.payhere.accountbook.services.customer.infrastructure;

import com.payhere.accountbook.services.customer.domain.Customer;
import com.payhere.accountbook.services.customer.domain.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository
public class InmemoryCustomerRepository implements CustomerRepository {
    private HashMap<String, Customer> repo = new HashMap<>();

    @Override
    public void save(Customer customer) {
        repo.put(customer.getEmail(), customer);
    }

    @Override
    public boolean existByEmail(String email) {
        return repo.containsKey(email);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.ofNullable(repo.get(email));
    }
}
