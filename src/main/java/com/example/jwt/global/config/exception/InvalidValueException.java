package com.example.jwt.global.config.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class InvalidValueException extends BusinessException {

    public InvalidValueException(String value) {
        super(value, ErrorCode.INVALID_INPUT_VALUE);
    }

    public InvalidValueException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}