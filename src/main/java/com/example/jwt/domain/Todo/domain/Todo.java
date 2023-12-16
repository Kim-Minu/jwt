package com.example.jwt.domain.Todo.domain;

import com.example.jwt.domain.Todo.dto.TodoUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "done", nullable = false)
    private boolean done;

    public void update(TodoUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.done = requestDto.isDone();
    }

}
