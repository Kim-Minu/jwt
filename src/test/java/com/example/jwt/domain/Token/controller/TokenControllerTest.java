package com.example.jwt.domain.Token.controller;

import com.example.jwt.domain.Token.domain.RefreshToken;
import com.example.jwt.domain.Token.domain.RefreshTokenRepository;
import com.example.jwt.domain.Token.dto.CreateAccessTokenRequestDto;
import com.example.jwt.domain.User.domain.User;
import com.example.jwt.domain.User.domain.UserRepository;
import com.example.jwt.global.config.jwt.JwtFactory;
import com.example.jwt.global.config.jwt.JwtProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TokenControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void mocMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
        userRepository.deleteAll();

    }

    @DisplayName("createNewAccessToken: 새로운 엑세스 토큰을 발급한다.")
    @Test
    public void createNewAccessToken() throws Exception {

        // given - 테스트 유저를 생성하고, jjwt 라이브러리를 이용해 리프레시 토큰을 만들어 데이터베이스에 저장
        // 토큰 생성 API 여청 본문에 리프레시 토큰을 포함하여 요청 객체를 생성
        final String url = "/api/token";

        User testUser = userRepository.save(
                User.builder()
                        .email("user@email.com")
                        .password("test")
                        .build()
        );

        String refreshToken = JwtFactory.builder()
                .claims(Map.of("id", testUser.getId()))
                .build()
                .createToken(jwtProperties);

        refreshTokenRepository.save(
                new RefreshToken (
                    testUser.getId(),
                    refreshToken
                )
        );

        CreateAccessTokenRequestDto requestDto = new CreateAccessTokenRequestDto();
        requestDto.setRefreshToken(refreshToken);

        final String requestBody = objectMapper.writeValueAsString(requestDto);

        // when - 토큰 추가 API에 요청을 보낸다
        // 이때 요청 타입은 JSON이며, given 절에서 미리 만들어둔 객체를 요청 본문으로 함계 보낸다
        ResultActions resultActions = mockMvc.perform(
                    post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)

        );

        // then - 응답 코드가 201 Created 인지 확인하고 응답으로 온 액세스 토큰이 비어있지 않은지 확인한다.
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());

    }



}