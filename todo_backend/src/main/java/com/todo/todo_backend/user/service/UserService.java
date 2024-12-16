package com.todo.todo_backend.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.todo_backend.user.model.dto.entity.User;
import com.todo.todo_backend.user.repository.UserRepository;

@Service("UserService")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 유저 정보 조회
    public Optional<User> userInfo(String userId) {
        Optional<User> userInfo = userRepository.findById(userId);

        return userInfo;
    }

}
