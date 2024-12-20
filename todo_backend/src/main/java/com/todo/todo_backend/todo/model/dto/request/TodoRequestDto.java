package com.todo.todo_backend.todo.model.dto.request;

import lombok.Data;

@Data
public class TodoRequestDto {

    private String title;

    private String contents;
}
