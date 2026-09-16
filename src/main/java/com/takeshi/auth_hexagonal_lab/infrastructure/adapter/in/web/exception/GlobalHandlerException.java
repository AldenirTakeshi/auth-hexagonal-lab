package com.takeshi.auth_hexagonal_lab.infrastructure.adapter.in.web.exception;

import com.takeshi.auth_hexagonal_lab.domain.exception.EmailAlreadyRegisterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(EmailAlreadyRegisterException.class)
    public ResponseEntity<String> handleEmailAlreadyException(EmailAlreadyRegisterException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

}
