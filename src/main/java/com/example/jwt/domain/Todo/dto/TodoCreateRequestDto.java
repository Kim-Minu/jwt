package com.example.jwt.domain.Todo.dto;

import com.example.jwt.domain.Todo.domain.Todo;
import com.example.jwt.domain.User.domain.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor

public class TodoCreateRequestDto {

    private String title;

    private boolean done;

    public static Todo toEntity(final TodoCreateRequestDto requestDto, Long userId){
        return Todo.builder()
                .title(requestDto.title)
                .userId(userId)
                .done(requestDto.done)
                .build();
    }
}
