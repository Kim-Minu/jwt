package com.example.jwt.domain.Token.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class CreateAccessTokenResponseDto {
    private String accessToken;
}
