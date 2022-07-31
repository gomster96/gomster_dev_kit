package com.gomster.exception.oauth;

import com.gomster.exception.GomsterLoginException;

import org.springframework.http.HttpStatus;

public abstract class OauthException extends GomsterLoginException {

    protected OauthException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
