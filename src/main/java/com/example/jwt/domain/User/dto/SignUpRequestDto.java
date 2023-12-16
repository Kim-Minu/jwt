package com.example.jwt.domain.User.dto;

import com.example.jwt.domain.User.domain.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    public static User toEntity(final SignUpRequestDto requestDto){
        return User.builder()
                .password(requestDto.password)
                .email(requestDto.email)
                .build();
    }
}
