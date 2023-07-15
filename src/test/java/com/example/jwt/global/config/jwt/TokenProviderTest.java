package com.example.jwt.global.config.jwt;

import com.example.jwt.domain.User.domain.Role;
import com.example.jwt.domain.User.domain.User;
import com.example.jwt.domain.User.domain.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generateToken(): 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {

        // given - 토큰에 유정 정보를 추가하기 위한 테스트 유저를 만듭니다.
        User testUser = userRepository.save(
                User.builder()
                        .email("user@email.com")
                        .password("test")
                        .build()
        );

        // when - 토큰 제공자의 generateToken() 메서드를 호출해 토큰을 만듭니다.
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        // then - jjwt 라이브러리를 사용해 토큰을 복호화합나다. 토큰을 만들 때 클레임으로 넣어둔 id 값이 given 절에서 만든 유저 id와 동일한지 확인합니다.
        Long userId = Jwts.parserBuilder()
                .setSigningKey(tokenProvider.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    @DisplayName("validToken(): 만로된 토큰인 떄에 유효성 검즈에 실패한다.")
    @Test
    void validToken() {

        // given - jjwt 라이브러리를 사용해 토큰을 생성합니다. 이때 만료 시간은 1970 1월 1일 부터 현재 시간을 밀리초 단위로 치환 한 값에
        // 1000을 빼, 이미 만료된 토큰으로 생성합니다.
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build().createToken(jwtProperties);

        // when - 토큰 제공자의 validToken() 메서드를 호ㅜㄹ해 유효한 토큰인지 검증한 뒤 결과값을 반환받습니다.
        boolean result = tokenProvider.validToken(token);

        // then - 반환값이 false (유요한 토큰이 아님)인 것을 확인합니다.
        assertThat(result).isFalse();

    }

    @DisplayName("getAuthentication(): 토큰 기반으로 인증 정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {

        String userEmail = "user@email.com";

        // given - jjwt 라이브러리를 사용해 토큰을 생성합ㄴ디ㅏ. 이때 토큰의 제목인 subject는 "user@email.com" 라는 값을 사용
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build().createToken(jwtProperties);

        // when - 토큰 제공자의 getAuthentication() 메서드를 호출해 인증 객체를 반환합니다.
        Authentication authentication = tokenProvider.getAuthentication(token);

        // then - 반환받은 인증 객체의 우저 이름을 가져와 given절에서 설정한 subject 값인 "user@email.com"과 같은지 확인합니다.
        assertThat( ((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);

    }

    @DisplayName("getUserId(): 토큰으로 유저 ID를 가져올 수 있다.")
    @Test
    void getUserId() {

        // given - jjwt라이브러리를 사용해 토큰을 생성합니다. 이때 클레임을 추가합나다. 키는 "id" 값은 1이라는 유저 ID 입니다.
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);

        // when - 토큰 제공자의 getUserId() 메서드를 호출해 유저 ID를 반환받습니다.
        Long userIdByToken = tokenProvider.getUserId(token);

        // then - 반환받은 유저 ID가 given절에서 설정한 유저 ID값인 1과 같은지 확인합니다.
        assertThat(userIdByToken).isEqualTo(userId);
    }
}