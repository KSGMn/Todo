package com.todo.todo_backend.todo.model.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoResponseDto {
    private Integer id;
    private String username;
    private String title;
    private String contents;
    private LocalDate targetDate;
}
