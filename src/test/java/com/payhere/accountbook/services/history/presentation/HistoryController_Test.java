package com.payhere.accountbook.services.history.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payhere.accountbook.services.customer.domain.Customer;
import com.payhere.accountbook.services.customer.domain.CustomerRepository;
import com.payhere.accountbook.web.security.JwtTokenFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HistoryController_Test {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    @DisplayName("가계부는 고객만 등록할 수 있음")
    void notAccess_401() throws Exception {
        // when
        String token = "";
        RegisterHistoryRequest registerHistoryRequest = RegisterHistoryRequest.of(1000, "memo");
        assertRegisterHistory(token, registerHistoryRequest)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("가계부 등록시 금액이 0원 이하일 경우 400 에러")
    void invalidMoney_400() throws Exception {
        // when
        String token = obtainJwtToken("email");
        RegisterHistoryRequest registerHistoryRequest = RegisterHistoryRequest.of(0, "memo");
        assertRegisterHistory(token, registerHistoryRequest)

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("가계부 등록시 메모를 입력하지 않을 경우 400 에러")
    void emptyMemo_400() throws Exception {
        // when
        String token = obtainJwtToken("email");
        RegisterHistoryRequest registerHistoryRequest = RegisterHistoryRequest.of(1100, "");
        assertRegisterHistory(token, registerHistoryRequest)

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("가계부 등록")
    void registerHistory() throws Exception {
        // when
        String token = obtainJwtToken("email");
        RegisterHistoryRequest registerHistoryRequest = RegisterHistoryRequest.of(1100, "메모");
        assertRegisterHistory(token, registerHistoryRequest)

        // then
        .andExpect(status().isCreated());
    }

    @Autowired CustomerRepository customerRepository;
    @Autowired JwtTokenFactory jwtTokenFactory;
    private String obtainJwtToken(String email){
        if(!customerRepository.existByEmail(email)){
            customerRepository.save(Customer.of(email, email));
        }
        User user = new User(email, email, Arrays.asList(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
        return jwtTokenFactory.createToken(user).get();
    }

    private final String AUTHENTICATION = "Authentication";
    private final String REGISTER_HISTORY_URL = "/api/history";
    private ResultActions assertRegisterHistory(String token, RegisterHistoryRequest registerHistoryRequest) throws Exception {
        return mockMvc.perform(post(REGISTER_HISTORY_URL)
                .header(AUTHENTICATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerHistoryRequest)));
    }

}
