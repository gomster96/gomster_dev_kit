package com.gomster.exception;

import org.springframework.http.HttpStatus;

public class OpenApiServerException extends GomsterLoginException {

    public OpenApiServerException() {
        super("외부 api 서버에 문제가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
