package com.payhere.accountbook.services.customer.presentation;

import com.payhere.accountbook.services.customer.application.DuplicateCustomerEmailException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerExceptionHandler {
    @ExceptionHandler(DuplicateCustomerEmailException.class)
    public ResponseEntity<String> error(DuplicateCustomerEmailException e){
        return ResponseEntity.badRequest().body("이미 다른 고객이 위 이메일을 사용중입니다.");
    }
}
