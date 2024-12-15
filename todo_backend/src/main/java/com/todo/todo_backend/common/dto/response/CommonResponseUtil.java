package com.todo.todo_backend.common.dto.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonResponseUtil {
    public static <T> CommonResponseDto<T> createResponse(String message, T data) {
        return CommonResponseDto.<T>builder()
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ResponseEntity<CommonResponseDto<T>> createResponseEntity(String message, T data,
            HttpStatus status) {
        CommonResponseDto<T> response = createResponse(message, data);
        return ResponseEntity.status(status).body(response);
    }
}
