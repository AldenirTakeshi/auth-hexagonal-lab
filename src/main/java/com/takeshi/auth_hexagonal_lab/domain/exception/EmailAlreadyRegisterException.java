package com.takeshi.auth_hexagonal_lab.domain.exception;

public class EmailAlreadyRegisterException extends RuntimeException{
    public EmailAlreadyRegisterException(String message) {
        super(message);
    }
}
