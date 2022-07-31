package com.gomster.login.auth.presentation;

import com.gomster.login.auth.application.AuthService;
import com.gomster.login.auth.application.dto.LoginRequestDto;
import com.gomster.login.auth.application.dto.LoginResponseDto;
import com.gomster.login.auth.presentation.response.TokenResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/auth/{oauthProvider}/login/token")
    public ResponseEntity<TokenResponse> login(@PathVariable String oauthProvider, @RequestParam String code) {
        //Authorization code 를 넘김
        System.out.println("check: " + code);
        LoginResponseDto loginResponseDto = authService.login(new LoginRequestDto(oauthProvider, code));
        System.out.println(loginResponseDto.getToken());
        return ResponseEntity.ok(TokenResponse.from(loginResponseDto));
    }
}