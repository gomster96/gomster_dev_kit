package com.gomster.login.auth.presentation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gomster.login.auth.domain.RequiredLogin;
import com.gomster.login.auth.infrastructure.JwtProvider;
import com.gomster.login.util.AuthorizationExtractor;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {
    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(isPreflight(request)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (!handlerMethod.hasMethodAnnotation(RequiredLogin.class)) {
            return true;
        }
        String token = AuthorizationExtractor.extractAccessToken(request);
        jwtProvider.validateToken(token);
        return true;
    }

    private boolean isPreflight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }
}