package com.example.jwt.domain.Token.service;

import com.example.jwt.domain.Token.domain.RefreshTokenRepository;
import com.example.jwt.domain.Token.dto.CreateAccessTokenRequestDto;
import com.example.jwt.domain.Token.dto.CreateAccessTokenResponseDto;
import com.example.jwt.domain.User.domain.User;
import com.example.jwt.domain.User.service.UserService;
import com.example.jwt.global.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public CreateAccessTokenResponseDto createNewAccessToken(CreateAccessTokenRequestDto requestDto) {

        String refreshToken = requestDto.getRefreshToken();

        // 토큰 유효성 검사에 실파하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);

        return CreateAccessTokenResponseDto.builder()
                .accessToken(tokenProvider.generateToken(user, Duration.ofHours(2)))
                .build();
    }
}
