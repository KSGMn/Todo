package com.todo.todo_backend.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.todo_backend.auth.model.dto.request.CheckCertificationRequestDto;
import com.todo.todo_backend.auth.model.dto.request.CreateTestUserRequestDto;
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
    @PostMapping("/email-certification")
    public ResponseEntity<?> emailCertification(@RequestBody EmailCertificationRequestDto dto) {
        authService.emailCertification(dto);
        return ResponseEntity.ok("Success");
    }

    // 중복확인 및 인증번호 생성 후 이메일 전송
    @PostMapping("/check-email-certification")
    public ResponseEntity<?> checkEmailCertification(@RequestBody CheckCertificationRequestDto dto) {
        authService.checkCertification(dto);
        return ResponseEntity.ok("Success");
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto dto) {
        authService.signUp(dto);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody TokenRequestDto tokenRequest,
            HttpServletResponse response) {
        TokenResponseDto tokens = authService.signIn(tokenRequest, response);
        return ResponseEntity.ok(tokens);

    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenResponseDto refreshTokenRequest,
            HttpServletResponse response) {
        ResponseEntity<TokenResponseDto> refreshToken = authService.refreshToken(refreshTokenRequest, response);
        return ResponseEntity.ok(refreshToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response, @CookieValue("accessToken") String accessToken,
            @CookieValue("refreshToken") String refreshToken) {

        long accessTokenExpirationTime = 30 * 60 * 1000L; // 예: 30분
        long refreshTokenExpirationTime = 7 * 24 * 60 * 60 * 1000L; // 예: 7일

        authService.logout(response, accessToken, refreshToken, accessTokenExpirationTime, refreshTokenExpirationTime);
        return ResponseEntity.ok("Logout Success");
    }

    @PostMapping("/create-test-user")
    public ResponseEntity<?> createTestUser(@RequestBody CreateTestUserRequestDto dto) {
        authService.createTestUser(dto);
        return ResponseEntity.ok("Success");
    }

}
