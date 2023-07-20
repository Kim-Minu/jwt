package com.example.jwt.domain.Todo.service;

import com.example.jwt.domain.Todo.domain.Todo;
import com.example.jwt.domain.Todo.domain.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoServiceTest {

    @Autowired
    private TodoRepository todoRepository;

    @DisplayName("todo_생성하기_테스트")
    @Test
    public void todo_생성하기_테스트() {
        Todo todo = Todo.builder()
                .title("test")
                .userId(1L)
                .done(false)
                .build();

        todoRepository.save(todo);

        Todo saveEntity = todoRepository.findById(todo.getId()).get();


        assertThat(saveEntity.getTitle()).isEqualTo(todo.getTitle());

    }

}