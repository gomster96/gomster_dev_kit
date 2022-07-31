package com.gomster.login.member.application.dto;

import com.gomster.login.member.presentation.request.MemberCreateRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberCreateDto {
    private String email;
    private String oauthProvider;
    private String nickname;

    public static MemberCreateDto from(MemberCreateRequest request) {
        return new MemberCreateDto(request.getEmail(), request.getOauthProvider(), request.getNickname());
    }
}