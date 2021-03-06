package com.gomster.login.auth.infrastructure;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import com.gomster.exception.oauth.GetAccessTokenException;
import com.gomster.exception.oauth.GetUserInfoException;
import com.gomster.exception.oauth.UnableToGetOauthResponseException;
import com.gomster.login.auth.domain.OauthProvider;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class ApiRequester {

    public Map<String, Object> getUserInfo(String code, OauthProvider oauthProvider) {
        String token = getToken(code, oauthProvider);
        return getUserInfoByToken(token, oauthProvider.getUserInfoUrl());
    }

    private String getToken(String code, OauthProvider oauthProvider) {
        System.out.println("check: " + oauthProvider.getTokenUrl());
        Map<String, Object> responseBody = WebClient.create()
                                                    .post()
                                                    .uri(oauthProvider.getTokenUrl())
                                                    .headers(header -> {
                                                        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                                                        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                                                        header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                                                    })
                                                    .bodyValue(tokenRequest(code, oauthProvider))
                                                    .retrieve()
                                                    .onStatus(HttpStatus::isError, response ->
                                                            response.bodyToMono(String.class)
                                                                    .flatMap(error -> Mono.error(new UnableToGetOauthResponseException(error))))
                                                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                                                    })
                                                    .flux()
                                                    .toStream()
                                                    .findFirst()
                                                    .orElseThrow(GetAccessTokenException::new);
        validateResponseBody(responseBody);
        return responseBody.get("access_token").toString();
    }

    private MultiValueMap<String, String> tokenRequest(String code, OauthProvider oauthProvider) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", oauthProvider.getRedirectUrl());
        formData.add("client_id", oauthProvider.getClientId());
        formData.add("client_secret", oauthProvider.getClientSecret());
        return formData;
    }

    private void validateResponseBody(Map<String, Object> responseBody) {
        if (!responseBody.containsKey("access_token")) {
            throw new GetAccessTokenException();
        }
    }

    private static Map<String, Object> getUserInfoByToken(String token, String userInfoUri) {
        Map<String, Object> responseBody = WebClient.create()
                                                    .get()
                                                    .uri(userInfoUri)
                                                    .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                                                    .retrieve()
                                                    .onStatus(HttpStatus::isError, response ->
                                                            response.bodyToMono(String.class)
                                                                    .flatMap(error -> Mono.error(new UnableToGetOauthResponseException(error))))
                                                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                                                    })
                                                    .flux()
                                                    .toStream()
                                                    .findFirst()
                                                    .orElseThrow(GetUserInfoException::new);

        return responseBody;
    }
}
