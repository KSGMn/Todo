package com.todo.todo_backend.common.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CommonResponseDto<T> {
    private String message;
    private T data;
    private LocalDateTime timestamp;

}
