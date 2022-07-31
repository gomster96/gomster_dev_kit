package com.gomster.login.member.presentation;

import java.net.URI;

import com.gomster.login.auth.domain.Login;
import com.gomster.login.auth.domain.LoginMember;
import com.gomster.login.auth.domain.RequiredLogin;
import com.gomster.login.auth.presentation.response.TokenResponse;
import com.gomster.login.member.application.MemberService;
import com.gomster.login.member.application.dto.MemberCreateDto;
import com.gomster.login.member.application.dto.MemberCreateResponseDto;
import com.gomster.login.member.presentation.request.MemberCreateRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<TokenResponse> create(@RequestBody MemberCreateRequest request) {

        MemberCreateResponseDto responseDto = memberService.save(MemberCreateDto.from(request));
        URI location = URI.create("/api/members/" + responseDto.getMemberId());
        return ResponseEntity.created(location)
                             .body(new TokenResponse(responseDto.getToken()));
    }

    @GetMapping
    @RequiredLogin
    public ResponseEntity<Long> testLoginInterceptor(@Login LoginMember loginMember){
        return ResponseEntity.ok(loginMember.getId());
    }
}
