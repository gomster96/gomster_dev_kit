package com.gomster.login.member.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.gomster.login.member.application.dto.MemberCreateDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String image;

    private String email;

    private String oauthProvider;

    public static Member from(MemberCreateDto memberCreateDto) {
        return Member.builder()
                     .nickname(memberCreateDto.getNickname())
                     .email(memberCreateDto.getEmail())
                     .oauthProvider(memberCreateDto.getOauthProvider())
                     .build();
    }
}
