package com.todo.todo_backend.user.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.todo_backend.common.exception.CustomExceptions;
import com.todo.todo_backend.user.model.dto.entity.User;
import com.todo.todo_backend.user.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> userInfo(@PathVariable String userId) {
        Optional<User> user = userService.userInfo(userId);

        if (user.isEmpty())
            throw new CustomExceptions.NotFoundException("Not Found userId: " + userId);

        return ResponseEntity.ok(user.get());
    }

}
