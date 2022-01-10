package com.payhere.accountbook.services.customer.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payhere.accountbook.services.customer.domain.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerController_Test {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입시 이메일을 입력하지 않을 경우 400 에러")
    void emptyEmail_400() throws Exception {
        // when
        SignUpRequest signUpRequest = SignUpRequest.of("", "password");
        assertSignUp(signUpRequest)

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입시 이메일 형식이 유효하지 않을 경우 400 에러")
    void invalidEmail_400() throws Exception {
        // when
        SignUpRequest signUpRequest = SignUpRequest.of("invalidEmail", "password");
        assertSignUp(signUpRequest)

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입시 비밀번호를 입력하지 않을 경우 400 에러")
    void emptyPassword_400() throws Exception {
        // when
        SignUpRequest signUpRequest = SignUpRequest.of("email@google.com", "");
        assertSignUp(signUpRequest)

        // then
        .andExpect(status().isBadRequest());
    }

    @Autowired
    StubCustomerRepository customerRepository;

    @Test
    @DisplayName("이미 해당 이메일로 가입한 고객이 존재하는 경우 400 에러")
    void duplicateEmail_400() throws Exception {
        // given
        Customer customer = Customer.of("email@google.com", "password");
        customerRepository.save(customer);

        // when
        SignUpRequest signUpRequest = SignUpRequest.of("email@google.com", "password");
        assertSignUp(signUpRequest)

        // then
        .andExpect(status().isBadRequest());
    }

    private final String SIGNUP_URL = "/api/customer";
    private ResultActions assertSignUp(SignUpRequest signUpRequest) throws Exception{
        return mockMvc.perform(post(SIGNUP_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)));
    }

}
