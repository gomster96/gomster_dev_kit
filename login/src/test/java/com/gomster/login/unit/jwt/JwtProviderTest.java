package com.gomster.login.unit.jwt;

import com.gomster.exception.authorization.InvalidTokenException;
import com.gomster.login.auth.infrastructure.JwtProvider;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.assertj.core.api.Assertions;

import static org.assertj.core.api.Assertions.*;

public class JwtProviderTest {
    @Test
    @DisplayName("jwt를 생성한다")
    public void createJwt(){
        //given
        JwtProvider jwtProvider = new JwtProvider("hihi", 100000L);

        //when
        String token = jwtProvider.createToken("1");
        //then
        assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("jwt payload 를 얻을 수 있다.")
    public void getPayLoadByJwt(){
        //given
        JwtProvider jwtProvider = new JwtProvider("hihi", 100000L);
        Long memberId = 1L;
        String token = jwtProvider.createToken(String.valueOf(memberId));
        //when
        String payLoad = jwtProvider.getPayLoad(token);
        //then
        assertThat(payLoad).isEqualTo(memberId.toString());
    }

    @Test
    @DisplayName("올바르지 않은 토큰이 오면, InvalidToke nException이 일어난다.")
    void getInvalidToken() {
        //given
        JwtProvider jwtProvider = new JwtProvider("hihi", 8640000L);
        //when,then
        assertThatThrownBy(() -> jwtProvider.validateToken("invalidToken"))
                .isInstanceOf(InvalidTokenException.class);
    }
}
