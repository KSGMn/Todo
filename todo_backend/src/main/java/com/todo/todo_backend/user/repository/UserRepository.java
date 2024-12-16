package com.todo.todo_backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.todo_backend.user.model.dto.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    User findByUserId(String userName);

}
