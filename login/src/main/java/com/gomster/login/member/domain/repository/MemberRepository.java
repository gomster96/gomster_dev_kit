package com.gomster.login.member.domain.repository;

import java.util.Optional;

import com.gomster.login.member.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailAndOauthProvider(String email, String oauthProvider);
}
