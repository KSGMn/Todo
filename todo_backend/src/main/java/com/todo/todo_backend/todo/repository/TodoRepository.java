package com.todo.todo_backend.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.todo_backend.todo.model.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

}
