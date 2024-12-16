package com.todo.todo_backend.auth.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;

import com.todo.todo_backend.auth.CertificationNumber;
import com.todo.todo_backend.auth.model.dto.entity.Certification;
import com.todo.todo_backend.auth.model.dto.request.CheckCertificationRequestDto;
import com.todo.todo_backend.auth.model.dto.request.CreateTestUserRequestDto;
import com.todo.todo_backend.auth.model.dto.request.EmailCertificationRequestDto;
import com.todo.todo_backend.auth.model.dto.request.IdCheckRequestDto;
import com.todo.todo_backend.auth.model.dto.request.SignUpRequestDto;
import com.todo.todo_backend.auth.model.dto.request.TokenRequestDto;
import com.todo.todo_backend.auth.model.dto.response.TokenResponseDto;
import com.todo.todo_backend.auth.repository.CertificationRepository;
import com.todo.todo_backend.common.exception.CustomExceptions;
import com.todo.todo_backend.common.exception.CustomExceptions.ConflictException;
import com.todo.todo_backend.provider.EmailProvider;
import com.todo.todo_backend.user.model.dto.entity.User;
import com.todo.todo_backend.user.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service("AuthService")
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailProvider emailProvider;

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 유저 아이디 중복 확인
    public void idCheck(IdCheckRequestDto dto) {

        String userId = dto.getUserId();

        boolean isExistId = userRepository.existsById(userId);

        if (isExistId)
            throw new CustomExceptions.ConflictException("duplication userId: " + dto.getUserId());

    }

    // 이메일 인증번호 전송 및 중복확인
    public void emailCertification(EmailCertificationRequestDto dto) {

        String userId = dto.getUserId();
        String email = dto.getEmail();

        // 유저 아이디 중복확인
        boolean isExistId = userRepository.existsById(userId);
        if (isExistId)
            throw new ConflictException("duplication userId: " + userId);
        // 이메일 중복확인
        boolean isExistedEmail = userRepository.existsByEmail(email);
        if (isExistedEmail)
            throw new ConflictException("duplication email: " + email);

        // 인증번호 생성 후 전송
        String certificationNumber = CertificationNumber.getCertificationNumber();
        boolean isSuccessed = emailProvider.sendCertificationMail(email, certificationNumber);
        if (!isSuccessed)
            throw new CustomExceptions.BadRequestException("mail send fail");

        // 인증번호를 검증하기 위해 생성된 인증번호와 유저 정보를 저장
        Certification certification = new Certification(userId, email, certificationNumber);
        certificationRepository.save(certification);

    }

    // 인증번호 검증
    public void checkCertification(CheckCertificationRequestDto dto) {

        String userId = dto.getUserId();
        String email = dto.getEmail();
        String certificationNumber = dto.getCertificationNumber();

        Optional<Certification> certification = certificationRepository.findById(userId);

        if (certification.isEmpty())
            throw new CustomExceptions.UnauthorizedException("Authentication Failed");

        boolean isMatched = certification.get().getEmail().equals(email)
                && certification.get().getCertificationNumber().equals(certificationNumber);

        if (!isMatched)
            throw new CustomExceptions.UnauthorizedException("Authentication Failed");

    }

    // 회원가입
    public void signUp(SignUpRequestDto dto) {

        String userId = dto.getUserId();
        boolean isExistId = userRepository.existsById(userId);
        if (isExistId)
            throw new ConflictException("duplication userId: " + userId);

        String email = dto.getEmail();
        boolean isExistedEmail = userRepository.existsByEmail(email);
        if (isExistedEmail)
            throw new ConflictException("duplication email: " + email);

        String userName = dto.getUserName();
        boolean isExistedNickName = userRepository.existsByUserName(userName);
        if (isExistedNickName)
            throw new ConflictException("duplication user name: " + userName);

        String certificationNumber = dto.getCertificationNumber();
        Optional<Certification> certification = certificationRepository.findById(userId);
        boolean isMatched = certification.get().getEmail().equals(email)
                && certification.get().getCertificationNumber().equals(certificationNumber);
        if (!isMatched)
            throw new CustomExceptions.UnauthorizedException("Authentication Failed");

        String password = dto.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        dto.setPassword(encodedPassword);

        User userEntity = User.builder()
                .userId(dto.getUserId())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .userName(dto.getUserName())
                .role("USER")
                .signupDate(LocalDate.now())
                .build();
        userRepository.save(userEntity);

        certificationRepository.deleteById(userId);

    }

    // 로그인
    public TokenResponseDto signIn(@RequestBody TokenRequestDto tokenRequest,
            HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(tokenRequest.getUserId(),
                        tokenRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String userId = tokenRequest.getUserId();
        // 액세스 토큰 생성
        TokenResponseDto accessToken = jwtTokenService.generateAccessToken(userId);
        // 리프레시 토큰 생성
        TokenResponseDto refreshToken = jwtTokenService.generateRefreshToken(userId);

        TokenResponseDto responseToken = TokenResponseDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken.getAccessToken())
                .refreshToken(refreshToken.getRefreshToken())
                .build();

        // 리프레시 토큰을 Redis에 저장, 유효기간 7일
        redisTemplate.opsForValue().set("rt-" + refreshToken.getRefreshToken(), "rt-" + userId, 7, TimeUnit.DAYS);

        // 쿠키 생성 및 설정
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken.getAccessToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(60 * 15); // 15분

        Cookie refreshTokenCookie = new Cookie("refreshToken",
                refreshToken.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7); // 7일

        // 응답에 쿠키 추가
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return responseToken;

    }

    public ResponseEntity<TokenResponseDto> refreshToken(@RequestBody TokenResponseDto refreshTokenRequest,
            HttpServletResponse response) {
        String refreshToken = refreshTokenRequest.getRefreshToken().substring(3);

        // 리프레시 토큰의 유효성 검사 및 사용자 이름 추출
        if (jwtTokenService.validateRefreshToken(refreshToken)) {
            String getRedisUserId = (String) redisTemplate.opsForValue().get(refreshTokenRequest.getRefreshToken());
            String userId = getRedisUserId.substring(3);
            TokenResponseDto newAccessToken = jwtTokenService.generateAccessToken(userId);

            // 새 액세스 토큰으로 Token 객체 생성 (리프레시 토큰은 재발급하지 않음)
            TokenResponseDto responseToken = TokenResponseDto.builder()
                    .grantType("Bearer")
                    .accessToken(newAccessToken.getAccessToken())
                    .build();

            Cookie accessTokenCookie = new Cookie("accessToken", newAccessToken.getAccessToken());
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(60 * 15); // 15분

            response.addCookie(accessTokenCookie);

            return ResponseEntity.ok(responseToken);
        } else {
            return ResponseEntity.status(401).body(null); // 유효하지 않은 리프레시 토큰 처리
        }
    }

    public ResponseEntity<?> logout(HttpServletResponse response, @CookieValue("accessToken") String accessToken,
            @CookieValue("refreshToken") String refreshToken, long accessTokenExpirationTime,
            long refreshTokenExpirationTime) {

        // 블랙리스트에 추가
        redisTemplate.opsForValue().set("blacklist:" + accessToken, "true", accessTokenExpirationTime,
                TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set("blacklist:" + refreshToken, "true", refreshTokenExpirationTime,
                TimeUnit.MILLISECONDS);

        // 기존 리프레시 토큰 삭제
        redisTemplate.delete(refreshToken);

        // 액세스 토큰 쿠키 만료
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0); // 쿠키를 즉시 만료시킴
        response.addCookie(accessTokenCookie);

        // 리프레시 토큰 쿠키도 만료
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0); // 쿠키를 즉시 만료시킴
        response.addCookie(refreshTokenCookie);

        // 로그아웃 성공 응답
        return ResponseEntity.ok("Logged out successfully");
    }

    public void createTestUser(CreateTestUserRequestDto dto) {

        String password = dto.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        dto.setPassword(encodedPassword);

        for (int i = 0; i < 10000; i++) {
            User userEntity = User.builder()
                    .userId("testUser" + i)
                    .password(dto.getPassword())
                    .email("test" + i + "@gmail.com")
                    .userName("testUser" + i)
                    .role("USER")
                    .signupDate(LocalDate.now())
                    .build();
            userRepository.save(userEntity);
        }

    }

}
