package com.gomster.login.auth.presentation;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;

import com.gomster.login.auth.application.AuthService;
import com.gomster.login.auth.domain.Login;
import com.gomster.login.util.AuthorizationExtractor;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = AuthorizationExtractor
                .extractAccessToken(Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class)));
        return authService.findMemberByToken(accessToken);
    }
}
