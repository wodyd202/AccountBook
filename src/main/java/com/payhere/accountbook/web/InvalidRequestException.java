package com.payhere.accountbook.web;

import lombok.AllArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class InvalidRequestException extends RuntimeException {
    private Errors errors;
    public List<String> getAllMessages (){
        return errors.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
    }

    public static void verifyNotHasErrors(Errors errors){
        if(errors.hasErrors()){
            throw new InvalidRequestException(errors);
        }
    }
}
