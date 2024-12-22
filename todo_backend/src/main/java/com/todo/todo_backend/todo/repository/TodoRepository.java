package com.todo.todo_backend.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.todo.todo_backend.todo.model.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    @Modifying
    @Query(value = "UPDATE todo SET done = false WHERE recycle = true AND done = true", nativeQuery = true)
    int resetDoneForRecycledTodos();

    List<Todo> findAllByUsername(String username);

}
