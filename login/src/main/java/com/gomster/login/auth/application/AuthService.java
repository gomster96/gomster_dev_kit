package com.gomster.login.auth.application;


import com.gomster.exception.oauth.NoSuchOAuthMemberException;
import com.gomster.login.auth.application.dto.LoginRequestDto;
import com.gomster.login.auth.application.dto.LoginResponseDto;
import com.gomster.login.auth.domain.LoginMember;
import com.gomster.login.auth.domain.OauthUserInfo;
import com.gomster.login.auth.infrastructure.JwtProvider;
import com.gomster.login.auth.infrastructure.OauthHandler;
import com.gomster.login.member.application.MemberService;
import com.gomster.login.member.domain.Member;
import com.gomster.login.member.domain.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final OauthHandler oauthHandler;
    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        String oauthProvider = loginRequestDto.getOauthProvider();
        OauthUserInfo userInfo = oauthHandler.getUserInfoFromCode(oauthProvider, loginRequestDto.getCode());
        String email = userInfo.getEmail();
        Member member = memberRepository.findByEmailAndOauthProvider(email, oauthProvider)
                                        .orElseThrow(() -> new NoSuchOAuthMemberException(email));

        String token = jwtProvider.createToken(String.valueOf(member.getId()));
        LoginResponseDto response = new LoginResponseDto(token);

        return response;
    }

    @Transactional(readOnly = true)
    public LoginMember findMemberByToken(String token) {
        if (!jwtProvider.isValidToken(token)) {
            return LoginMember.anonymous();
        }

        String payLoad = jwtProvider.getPayLoad(token);
        Long id = Long.parseLong(payLoad);
        Member member = memberService.findById(id);
        return new LoginMember(member.getId(), member.getEmail());
    }
}
