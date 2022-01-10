package com.payhere.accountbook.services.customer.presentation;

import com.payhere.accountbook.services.customer.application.CustomerRecord;
import com.payhere.accountbook.services.customer.application.CustomerService;
import com.payhere.accountbook.web.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.payhere.accountbook.web.InvalidRequestException.verifyNotHasErrors;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<CustomerRecord> signUp(@Valid @RequestBody SignUpRequest signUpRequest, Errors errors){
        verifyNotHasErrors(errors);
        signUpRequest.encodePassword(passwordEncoder);
        CustomerRecord customerRecord = customerService.signUp(signUpRequest.toSignUp());
        return ResponseEntity.status(HttpStatus.CREATED).body(customerRecord);
    }
}
