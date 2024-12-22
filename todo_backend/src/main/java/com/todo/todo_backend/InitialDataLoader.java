package com.todo.todo_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.todo.todo_backend.user.model.dto.entity.User;
import com.todo.todo_backend.user.repository.UserRepository;

@Configuration
public class InitialDataLoader {

    @Autowired
    private UserRepository userRepository; // JPA Repository 또는 다른 DAO 클래스 사용

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {

            // 초기 유저 데이터
            String rawPassword = "!Qa12345678";
            String encodedPassword = passwordEncoder.encode(rawPassword);

            if (!userRepository.existsByEmail("admin@example.com")) {
                User adminUser = new User();
                adminUser.setUserId("admin");
                adminUser.setEmail("admin@example.com");
                adminUser.setPassword(encodedPassword);
                adminUser.setUserName("Admin");
                adminUser.setRole("ADMIN");
                adminUser.setSignupDate(java.time.LocalDate.now());

                userRepository.save(adminUser);
                System.out.println("Admin user created");
            } else {
                System.out.println("Admin user already exists");
            }
        };
    }
}