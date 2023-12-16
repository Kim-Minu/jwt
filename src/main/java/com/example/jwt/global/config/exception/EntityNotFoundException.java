package com.example.jwt.global.config.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
