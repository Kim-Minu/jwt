package com.example.jwt.domain.Todo.service;

import com.example.jwt.domain.Todo.domain.Todo;
import com.example.jwt.domain.Todo.domain.TodoRepository;
import com.example.jwt.domain.Todo.dto.TodoCreateRequestDto;
import com.example.jwt.domain.Todo.dto.TodoResponseDto;
import com.example.jwt.domain.Todo.dto.TodoUpdateRequestDto;
import com.example.jwt.domain.User.domain.User;
import com.example.jwt.domain.User.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    private final UserRepository userRepository;


    /**
     * 로그인 유저로 todo List 조회
     * @param user
     * @return
     */
    public List<TodoResponseDto> list(User user) {
        Long userId = user.getId();

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        return todoRepository.findByUserId(userId)
                .stream()
                .map(TodoResponseDto::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public TodoResponseDto create(TodoCreateRequestDto requestDto, User user) {

        Long userId = user.getId();

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Todo todo = TodoCreateRequestDto.toEntity(requestDto, userId);

        todoRepository.save(todo);

        return new TodoResponseDto(todo);

    }

    @Transactional
    public void delete(Long todoId, User user) {

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("entity not found"));


        Long userId = todo.getUserId();

        if(!user.getId().equals(userId)){
            throw new RuntimeException("해당 회원의 todo가 아닙니다.");
        }

        todoRepository.delete(todo);

    }

    @Transactional
    public void update(TodoUpdateRequestDto requestDto, User user) {

        Long todoId = requestDto.getId();

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("entity not found"));

        Long userId = todo.getUserId();

        if(!user.getId().equals(userId)){
            throw new RuntimeException("해당 회원의 todo가 아닙니다.");
        }

        todo.update(requestDto);

        todoRepository.save(todo);
    }

}
