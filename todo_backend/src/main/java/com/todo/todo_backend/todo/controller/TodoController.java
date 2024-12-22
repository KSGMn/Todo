package com.todo.todo_backend.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.todo_backend.common.dto.response.CommonResponseDto;
import com.todo.todo_backend.common.exception.CustomExceptions;
import com.todo.todo_backend.todo.model.dto.request.TodoRequestDto;
import com.todo.todo_backend.todo.model.entity.Todo;
import com.todo.todo_backend.todo.service.TodoService;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    // 로그인 한 유저의 모든 Todo 조회
    @GetMapping
    public ResponseEntity<CommonResponseDto<List<Todo>>> findAllByUserId(@CookieValue("accessToken") String act) {
        return todoService.findAllByUserId(act);
    }

    // 모든 Todo 조회
    @GetMapping("/all")
    public ResponseEntity<CommonResponseDto<List<Todo>>> findAllTodo() {
        return todoService.findAllTodo();
    }

    // Todo 생성
    @PostMapping
    public ResponseEntity<CommonResponseDto<Todo>> createTodo(@RequestBody Todo todo) {
        try {
            return todoService.createTodo(todo);
        } catch (Exception e) {
            throw new CustomExceptions.BadRequestException("Create Fail.");
        }
    }

    // 반복할 Todo 생성
    @PostMapping("/add")
    public ResponseEntity<CommonResponseDto<Todo>> createRecycleTodo(@RequestBody Todo todo) {
        try {
            return todoService.createRecycleTodo(todo);
        } catch (Exception e) {
            throw new CustomExceptions.BadRequestException("Create Fail.");
        }
    }

    // Todo 업데이트
    @PostMapping("/{id}")
    public ResponseEntity<CommonResponseDto<Todo>> updateTodo(@RequestBody TodoRequestDto todoRequestDto,
            @PathVariable Integer id) {
        try {
            return todoService.updateTodo(todoRequestDto, id);
        } catch (Exception e) {
            throw new CustomExceptions.BadRequestException("Update Fail.");
        }
    }

    // Todo 완료 및 취소
    @PostMapping("/done/{id}")
    public ResponseEntity<?> doneTodo(@PathVariable Integer id) {
        try {
            todoService.doneTodo(id);
            return ResponseEntity.ok("Update Success.");
        } catch (Exception e) {
            throw new CustomExceptions.BadRequestException("Update Fail");
        }
    }

    // Todo 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Integer id) {
        try {
            todoService.deleteTodo(id);
            return ResponseEntity.ok("Delete Success.");
        } catch (Exception e) {
            throw new CustomExceptions.NotFoundException("Not Fount");
        }
    }

}
