package com.payhere.accountbook.services.history.presentation;

import com.payhere.accountbook.services.customer.domain.Customer;
import com.payhere.accountbook.services.customer.domain.CustomerRepository;
import com.payhere.accountbook.services.history.domain.History;
import com.payhere.accountbook.services.history.domain.HistoryRepository;
import com.payhere.accountbook.web.security.JwtTokenFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;

@SpringBootTest
public class HistoryIntegrationTest {
    @Autowired HistoryRepository historyRepository;
    protected History saveHistory(History history){
        historyRepository.save(history);
        return history;
    }

    @Autowired CustomerRepository customerRepository;
    @Autowired JwtTokenFactory jwtTokenFactory;
    protected String obtainJwtToken(String email){
        if(!customerRepository.existByEmail(email)){
            customerRepository.save(Customer.of(email, email));
        }
        User user = new User(email, email, Arrays.asList(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
        return jwtTokenFactory.createToken(user).get();
    }
}
