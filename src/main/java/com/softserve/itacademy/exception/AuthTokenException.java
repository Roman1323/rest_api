package com.softserve.itacademy.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.security.sasl.AuthenticationException;

@Getter
public class AuthTokenException extends AuthenticationException {
    private HttpStatus httpStatus;

    public AuthTokenException(String detail) {
        super(detail);
    }

    public AuthTokenException(String detail, HttpStatus httpStatus) {
        super(detail);
        this.httpStatus = httpStatus;
    }
}
