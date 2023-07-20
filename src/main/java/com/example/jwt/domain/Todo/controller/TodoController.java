package com.example.jwt.domain.Todo.controller;

import com.example.jwt.domain.Todo.domain.TodoRepository;
import com.example.jwt.domain.Todo.dto.TodoCreateRequestDto;
import com.example.jwt.domain.Todo.dto.TodoResponseDto;
import com.example.jwt.domain.Todo.dto.TodoUpdateRequestDto;
import com.example.jwt.domain.Todo.service.TodoService;
import com.example.jwt.domain.User.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/list")
    public ResponseEntity<List<TodoResponseDto>> list(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(todoService.list(user), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<TodoResponseDto> create(@RequestBody TodoCreateRequestDto requestDto, @AuthenticationPrincipal User user){

        return new ResponseEntity<>(
                todoService.create(requestDto, user),
                HttpStatus.CREATED
        );

    }

    @PutMapping("")
    public ResponseEntity<HttpStatus> update(@RequestBody TodoUpdateRequestDto requestDto, @AuthenticationPrincipal User user) {
        todoService.update(requestDto, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long todoId, @AuthenticationPrincipal User user) {

        todoService.delete(todoId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
