package com.gomster.login.member.presentation.request;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateRequest {

    @NotNull
    private String email;
    @NotNull
    private String nickname;
    @NotNull
    private String oauthProvider;


}

