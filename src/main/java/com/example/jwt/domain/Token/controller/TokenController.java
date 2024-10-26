package com.example.jwt.domain.Token.controller;

import com.example.jwt.domain.Token.dto.CreateAccessTokenRequestDto;
import com.example.jwt.domain.Token.dto.CreateAccessTokenResponseDto;
import com.example.jwt.domain.Token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {
    private final TokenService tokenService;

    public ResponseEntity<CreateAccessTokenResponseDto> createNewAccessToken(@RequestBody CreateAccessTokenRequestDto requestDto) {
        return new ResponseEntity<>(tokenService.createNewAccessToken(requestDto), HttpStatus.CREATED);
    }
}
