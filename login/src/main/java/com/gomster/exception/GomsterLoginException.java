package com.gomster.exception;

import org.springframework.http.HttpStatus;

public abstract class GomsterLoginException extends RuntimeException {
    private final HttpStatus httpStatus;

    public GomsterLoginException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}