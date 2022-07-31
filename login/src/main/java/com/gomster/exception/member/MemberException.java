package com.gomster.exception.member;

import com.gomster.exception.GomsterLoginException;

import org.springframework.http.HttpStatus;

public abstract class MemberException extends GomsterLoginException {
    public MemberException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
