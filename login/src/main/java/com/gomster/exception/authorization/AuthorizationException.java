package com.gomster.exception.authorization;

import com.gomster.exception.GomsterLoginException;

import org.springframework.http.HttpStatus;

public abstract class AuthorizationException extends GomsterLoginException {

    protected AuthorizationException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
