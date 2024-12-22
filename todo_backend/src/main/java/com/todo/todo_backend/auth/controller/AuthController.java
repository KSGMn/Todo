package com.todo.todo_backend.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.todo_backend.auth.model.dto.request.CheckCertificationRequestDto;
import com.todo.todo_backend.auth.model.dto.request.EmailCertificationRequestDto;
import com.todo.todo_backend.auth.model.dto.request.IdCheckRequestDto;
import com.todo.todo_backend.auth.model.dto.request.SignUpRequestDto;
import com.todo.todo_backend.auth.model.dto.request.TokenRequestDto;
import com.todo.todo_backend.auth.model.dto.response.TokenResponseDto;
import com.todo.todo_backend.auth.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/id-check")
    public ResponseEntity<?> idCheck(@RequestBody IdCheckRequestDto dto) {
        authService.idCheck(dto);
        return ResponseEntity.ok("Success");
    }

    // 중복확인 및 인증번호 생성 후 이메일 전송
    @PostMapping("/emails/certification")
    public ResponseEntity<?> emailCertification(@RequestBody EmailCertificationRequestDto dto) {
        authService.emailCertification(dto);
        return ResponseEntity.ok("Success");
    }

    // 중복확인 및 인증번호 생성 후 이메일 전송
    @PostMapping("/emails/verification")
    public ResponseEntity<?> checkEmailCertification(@RequestBody CheckCertificationRequestDto dto) {
        authService.checkCertification(dto);
        return ResponseEntity.ok("Success");
    }

    // 회원가입
    @PostMapping("/users")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto dto) {
        authService.signUp(dto);
        return ResponseEntity.ok("Success");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody TokenRequestDto tokenRequest,
            HttpServletResponse response) {
        TokenResponseDto tokens = authService.signIn(tokenRequest, response);
        return ResponseEntity.ok(tokens);

    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response, @CookieValue("accessToken") String act) {

        long accessTokenExpirationTime = 30 * 60 * 1000L; // 예: 30분
        long refreshTokenExpirationTime = 7 * 24 * 60 * 60 * 1000L; // 예: 7일

        authService.logout(response, act, accessTokenExpirationTime,
                refreshTokenExpirationTime);
        return ResponseEntity.ok("Logout Success");
    }

}
