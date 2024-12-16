package com.todo.todo_backend.user.model.dto.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC) // PROTECT로 하면 외부에서 접근이 불가능하여 public으로 변경해둔 상태
@AllArgsConstructor
@Table(name = "users")
@Entity
@Builder
public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "role", updatable = false, nullable = false)
    private String role;

    @Column(name = "signup_date", nullable = false, updatable = false)
    private LocalDate signupDate;
}
