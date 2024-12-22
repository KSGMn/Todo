package com.todo.todo_backend;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import com.todo.todo_backend.common.dto.response.CommonResponseDto;
import com.todo.todo_backend.todo.model.dto.request.TodoRequestDto;
import com.todo.todo_backend.todo.model.entity.Todo;
import com.todo.todo_backend.todo.repository.TodoRepository;
import com.todo.todo_backend.todo.service.TodoService;

@SpringBootTest
class TodoServiceTest {

    @Autowired
    private TodoService todoService;

    @MockBean
    private TodoRepository todoRepository;

    @Test
    void findByIdOrThrow_shouldReturnTodo_whenTodoExists() {
        // Given
        Integer id = 1;
        Todo todo = new Todo(1, "username", "Test Title", "Test Content", LocalDate.now(), false, false);
        Mockito.when(todoRepository.findById(id)).thenReturn(Optional.of(todo));

        // When
        Todo result = todoService.findByIdOrThrow(id);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Test Title", result.getTitle());
        Mockito.verify(todoRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void findByIdOrThrow_shouldThrowException_whenTodoDoesNotExist() {
        // Given
        Integer id = 999;
        Mockito.when(todoRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> todoService.findByIdOrThrow(id));
        Assertions.assertEquals("Not Found: " + id, exception.getMessage());
        Mockito.verify(todoRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void findAllTodo_shouldReturnAllTodos() {
        // Given
        List<Todo> todos = List.of(
                new Todo(1, "username", "Title1", "Content1", LocalDate.now(), false, false),
                new Todo(2, "username", "Title2", "Content2", LocalDate.now(), false, false));
        Mockito.when(todoRepository.findAll()).thenReturn(todos);

        // When
        ResponseEntity<CommonResponseDto<List<Todo>>> response = todoService.findAllTodo();

        // Then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.getBody().getData().size());
        Assertions.assertEquals("Success", response.getBody().getMessage());
        Mockito.verify(todoRepository, Mockito.times(1)).findAll();
    }

    @Test
    void createTodo_shouldSaveNewTodo() {
        // Given
        Todo todo = new Todo(null, "username", "Title", "Content", null, false, false);
        Todo savedTodo = new Todo(1, "username", "Title", "Content", LocalDate.now(), false, false);

        Mockito.when(todoRepository.save(Mockito.any(Todo.class))).thenReturn(savedTodo);

        // When
        ResponseEntity<CommonResponseDto<Todo>> response = todoService.createTodo(todo);

        // Then
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Create Success", response.getBody().getMessage());
        Assertions.assertEquals("Title", response.getBody().getData().getTitle());
        Mockito.verify(todoRepository, Mockito.times(1)).save(Mockito.any(Todo.class));
    }

    @Test
    void updateTodo_shouldUpdateExistingTodo() {
        // Given
        Integer id = 1;
        Todo existingTodo = new Todo(1, "username", "Old Title", "Old Content", LocalDate.now(), false, false);
        TodoRequestDto todoRequestDto = new TodoRequestDto("New Title", "New Content");

        Mockito.when(todoRepository.findById(id)).thenReturn(Optional.of(existingTodo));
        Mockito.when(todoRepository.save(Mockito.any(Todo.class))).thenReturn(existingTodo);

        // When
        ResponseEntity<CommonResponseDto<Todo>> response = todoService.updateTodo(todoRequestDto, id);

        // Then
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Update Success", response.getBody().getMessage());
        Assertions.assertEquals("New Title", response.getBody().getData().getTitle());
        Mockito.verify(todoRepository, Mockito.times(1)).findById(id);
        Mockito.verify(todoRepository, Mockito.times(1)).save(Mockito.any(Todo.class));
    }

    @Test
    void doneTodo_shouldToggleDoneStatus() {
        // Given
        Integer id = 1;
        Todo todo = new Todo(1, "username", "Title", "Content", LocalDate.now(), false, false);

        Mockito.when(todoRepository.findById(id)).thenReturn(Optional.of(todo));

        // When
        todoService.doneTodo(id);

        // Then
        Mockito.verify(todoRepository, Mockito.times(1)).findById(id);
        Mockito.verify(todoRepository, Mockito.times(1)).save(Mockito.any(Todo.class));
        Assertions.assertTrue(todo.isDone());
    }

    @Test
    void resetDoneForRecycledTodos_shouldUpdateRecycledTodos() {
        // Given
        Mockito.when(todoRepository.resetDoneForRecycledTodos()).thenReturn(5);

        // When
        todoService.resetDoneForRecycledTodos();

        // Then
        Mockito.verify(todoRepository, Mockito.times(1)).resetDoneForRecycledTodos();
    }
}
