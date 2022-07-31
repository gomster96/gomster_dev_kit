package com.gomster.login.member.presentation;

import java.net.URI;

import com.gomster.login.auth.presentation.response.TokenResponse;
import com.gomster.login.member.application.MemberService;
import com.gomster.login.member.application.dto.MemberCreateDto;
import com.gomster.login.member.application.dto.MemberCreateResponseDto;
import com.gomster.login.member.presentation.request.MemberCreateRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
