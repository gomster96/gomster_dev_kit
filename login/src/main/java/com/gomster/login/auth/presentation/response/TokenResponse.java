package com.gomster.login.auth.presentation.response;

import com.gomster.login.auth.application.dto.LoginResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {
    private String token;

    public static TokenResponse from(LoginResponseDto loginResponseDto) {
        return new TokenResponse(loginResponseDto.getToken());
    }
}

