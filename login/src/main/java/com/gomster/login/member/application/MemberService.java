package com.gomster.login.member.application;

import com.gomster.exception.member.MemberNotFoundException;
import com.gomster.login.auth.infrastructure.JwtProvider;
import com.gomster.login.auth.presentation.response.TokenResponse;
import com.gomster.login.member.application.dto.MemberCreateDto;
import com.gomster.login.member.application.dto.MemberCreateResponseDto;
import com.gomster.login.member.domain.Member;
import com.gomster.login.member.domain.repository.MemberRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    public MemberCreateResponseDto save(MemberCreateDto memberCreateDto) {
        Member savedMember = memberRepository.save(Member.from(memberCreateDto));
        String token = jwtProvider.createToken(String.valueOf(savedMember.getId()));
        return new MemberCreateResponseDto(savedMember.getId(), token);
    }
}
