package com.todo.todo_backend.todo.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodoRequestDto {

    private String title;

    private String contents;
}
