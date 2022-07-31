package com.gomster.login.config;

import java.util.Map;

import com.gomster.login.auth.domain.OauthProperties;
import com.gomster.login.auth.domain.OauthProvider;
import com.gomster.login.auth.infrastructure.ApiRequester;
import com.gomster.login.auth.infrastructure.OauthHandler;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(OauthProperties.class)
public class OauthConfig {

    private final OauthProperties properties;

    @Bean
    public OauthHandler oauthHandler() {
        Map<String, OauthProvider> providers = properties.getOauthProviders();
        return new OauthHandler(providers, new ApiRequester());
    }
}
