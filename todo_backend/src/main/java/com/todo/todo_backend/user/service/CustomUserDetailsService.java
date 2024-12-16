package com.todo.todo_backend.user.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.todo.todo_backend.common.exception.CustomExceptions.NotFoundException;
import com.todo.todo_backend.user.model.dto.entity.User;
import com.todo.todo_backend.user.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByUserId(username);

                if (user == null) {
                        throw new NotFoundException("User not found with username: " +
                                        username);
                }
                List<SimpleGrantedAuthority> authorities = Arrays.stream(user.getRole().split(","))
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());
                System.out.println(new org.springframework.security.core.userdetails.User(user.getUserName(),
                                user.getPassword(), authorities) + "커스텀 유저");
                return new org.springframework.security.core.userdetails.User(user.getUserName(),
                                user.getPassword(), authorities);
        }
}
