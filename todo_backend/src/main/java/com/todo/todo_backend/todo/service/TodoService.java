package com.todo.todo_backend.todo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
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

    // id로 Todo 찾기
    public Todo findByIdOrThrow(Integer id) {
        return todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found: " + id));
    }

    // 모든 Todo 조회
    public ResponseEntity<CommonResponseDto<List<Todo>>> findAllTodo() {

        List<Todo> todos = todoRepository.findAll();
        String message = todos.isEmpty() ? "Empty" : "Success";

        ResponseEntity<CommonResponseDto<List<Todo>>> response = CommonResponseUtil.createResponseEntity(message, todos,
                HttpStatus.OK);

        return response;
    }

    // Todo 생성
    public ResponseEntity<CommonResponseDto<Todo>> createTodo(Todo todo) {

        Todo createTodo = todo.builder()
                .username(todo.getUsername())
                .title(todo.getTitle())
                .contents(todo.getContents())
                .targetDate(LocalDate.now())
                .build();
        todoRepository.save(createTodo);
        String message = "Create Success";
        ResponseEntity<CommonResponseDto<Todo>> response = CommonResponseUtil.createResponseEntity(message,
                createTodo,
                HttpStatus.CREATED);
        return response;

    }

    // 반복할 Todo 생성
    public ResponseEntity<CommonResponseDto<Todo>> createRecycleTodo(Todo todo) {

        Todo createTodo = todo.builder()
                .username(todo.getUsername())
                .title(todo.getTitle())
                .contents(todo.getContents())
                .targetDate(LocalDate.now())
                .recycle(true)
                .build();
        todoRepository.save(createTodo);
        String message = "Create Success";
        ResponseEntity<CommonResponseDto<Todo>> response = CommonResponseUtil.createResponseEntity(message,
                createTodo,
                HttpStatus.CREATED);
        return response;

    }

    // Todo 업데이트
    public ResponseEntity<CommonResponseDto<Todo>> updateTodo(TodoRequestDto todoRequestDto, Integer id) {

        Todo findTodo = findByIdOrThrow(id);

        Todo updateTodo = Todo.builder()
                .id(findTodo.getId())
                .username(findTodo.getUsername())
                .title(todoRequestDto.getTitle() != null ? todoRequestDto.getTitle() : findTodo.getTitle())
                .contents(todoRequestDto.getContents() != null ? todoRequestDto.getContents() : findTodo.getContents())
                .targetDate(LocalDate.now())
                .build();
        todoRepository.save(updateTodo);

        String message = "Update Success";
        ResponseEntity<CommonResponseDto<Todo>> response = CommonResponseUtil.createResponseEntity(message,
                updateTodo,
                HttpStatus.CREATED);
        return response;
    }

    // Todo 완료 및 취소
    public void doneTodo(Integer id) {
        Todo findTodo = findByIdOrThrow(id);

        if (findTodo.isDone() == false) {
            findTodo.setDone(true);
            todoRepository.save(findTodo);
        } else {
            findTodo.setDone(false);
            todoRepository.save(findTodo);
        }
    }

    // Todo 삭제
    public void deleteTodo(Integer id) {

        Todo findTodo = findByIdOrThrow(id);

        if (findTodo.getId() != null) {
            todoRepository.deleteById(id);
        }
    }

    // 매일 00시에 실행
    @Scheduled(cron = "0 0 0 * * ?") // 매일 00:00에 실행
    public void resetDoneForRecycledTodos() {
        todoRepository.resetDoneForRecycledTodos();
        System.out.println("Recycle 완료 데이터 초기화가 수행되었습니다.");
    }
}
