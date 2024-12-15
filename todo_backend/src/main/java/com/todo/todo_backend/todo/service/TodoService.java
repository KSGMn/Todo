package com.todo.todo_backend.todo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.todo.todo_backend.common.dto.response.CommonResponseDto;
import com.todo.todo_backend.common.dto.response.CommonResponseUtil;
import com.todo.todo_backend.todo.model.dto.request.TodoRequestDto;
import com.todo.todo_backend.todo.model.entity.Todo;
import com.todo.todo_backend.todo.repository.TodoRepository;

@Service
public class TodoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TodoRepository todoRepository;

    public Todo findByIdOrThrow(Integer id) {
        return todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found: " + id));
    }

    public ResponseEntity<CommonResponseDto<List<Todo>>> findAllTodo() {

        List<Todo> todos = todoRepository.findAll();
        String message = todos.isEmpty() ? "Empty" : "Success";

        ResponseEntity<CommonResponseDto<List<Todo>>> response = CommonResponseUtil.createResponseEntity(message, todos,
                HttpStatus.OK);

        return response;
    }

    public ResponseEntity<CommonResponseDto<Todo>> createTodo(Todo todo) {

        Todo createTodo = todo.builder()
                .username(todo.getUsername())
                .title(todo.getTitle())
                .contents(todo.getTitle())
                .targetDate(LocalDate.now())
                .build();
        todoRepository.save(createTodo);
        String message = "Create Success";
        ResponseEntity<CommonResponseDto<Todo>> response = CommonResponseUtil.createResponseEntity(message,
                createTodo,
                HttpStatus.CREATED);
        return response;

    }

    public ResponseEntity<CommonResponseDto<Todo>> updateTodo(TodoRequestDto todoRequestDto, Integer id) {

        Todo findTodo = findByIdOrThrow(id);

        Todo updateTodo = Todo.builder()
                .id(findTodo.getId())
                .username(findTodo.getUsername())
                .title(todoRequestDto.getTitle())
                .contents(todoRequestDto.getContents())
                .targetDate(LocalDate.now())
                .build();
        todoRepository.save(updateTodo);

        String message = "Update Success";
        ResponseEntity<CommonResponseDto<Todo>> response = CommonResponseUtil.createResponseEntity(message,
                updateTodo,
                HttpStatus.CREATED);
        return response;
    }

    public void deleteTodo(Integer id) {

        Todo findTodo = findByIdOrThrow(id);

        if (findTodo.getId() != null) {
            todoRepository.deleteById(id);
        }
    }

}
