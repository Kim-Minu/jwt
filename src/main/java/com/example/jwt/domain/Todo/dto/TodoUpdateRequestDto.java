package com.example.jwt.domain.Todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TodoUpdateRequestDto {
    private Long id;

    private String title;

    private boolean done;
}
