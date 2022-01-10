package com.payhere.accountbook.services.history.presentation;

import com.payhere.accountbook.services.history.application.HistoryNotFoundException;
import com.payhere.accountbook.services.history.application.NoUpdateHistoryException;
import com.payhere.accountbook.services.history.domain.AlreadyRemovedHistoryException;
import com.payhere.accountbook.services.history.domain.NoUpdatePermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HistoryExceptionHandler {
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NoUpdateHistoryException.class)
    public void error(NoUpdateHistoryException e){}

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({HistoryNotFoundException.class, AlreadyRemovedHistoryException.class})
    public void error(RuntimeException e){}

    @ExceptionHandler(NoUpdatePermissionException.class)
    public ResponseEntity<String> error(NoUpdatePermissionException e){
        return ResponseEntity.badRequest().body("다른 고객의 가계부 이력을 수정할 수 없습니다.");
    }
}
