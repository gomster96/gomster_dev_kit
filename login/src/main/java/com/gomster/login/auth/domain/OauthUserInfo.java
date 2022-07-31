package com.gomster.login.auth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OauthUserInfo {
    private String email;
}
