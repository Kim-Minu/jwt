package com.example.jwt.domain.Todo.dto;


import com.example.jwt.domain.Todo.domain.Todo;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoResponseDto {
    private Long id;

    private String title;

    private boolean done;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.done = todo.isDone();
    }

}
