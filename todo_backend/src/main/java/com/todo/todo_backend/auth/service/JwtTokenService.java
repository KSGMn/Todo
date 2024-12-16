package com.todo.todo_backend.auth.service;

import java.security.KeyPair;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.todo.todo_backend.auth.model.dto.response.TokenResponseDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtTokenService {

    @Autowired
    private final KeyPair keyPair;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public TokenResponseDto generateAccessToken(String userId) {
        long expirationTime = TimeUnit.MINUTES.toMillis(15); // 15분
        String accessToken = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(keyPair.getPrivate(), io.jsonwebtoken.SignatureAlgorithm.RS256) // 개인키로 서명
                .compact();

        return TokenResponseDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    public TokenResponseDto generateRefreshToken(String userId) {
        long expirationTime = TimeUnit.DAYS.toMillis(7); // 7일
        String refreshToken = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(keyPair.getPrivate(), io.jsonwebtoken.SignatureAlgorithm.RS256) // 개인키로 서명
                .compact();

        return TokenResponseDto.builder()
                .grantType("Bearer")
                .refreshToken(refreshToken)
                .build();
    }

    public String getUsernameFromToken(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(keyPair.getPublic()) // 공개키로 검증
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean isTokenBlacklisted(String token) {
        return redisTemplate.hasKey("blacklist:" + token);
    }

    public boolean validateAccessToken(String token) {
        try {
            if (isTokenBlacklisted(token)) {
                return false;
            }
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(keyPair.getPublic()) // 공개키로 검증
                    .build()
                    .parseClaimsJws(token);

            // 만료 날짜 검사
            return !claims.getBody().getExpiration().before(new Date());
        } catch (io.jsonwebtoken.security.SignatureException ex) {
            // 서명 검증 실패
            return false;
        } catch (Exception e) {
            // 다른 모든 예외 처리
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            if (isTokenBlacklisted(token)) {
                return false;
            }
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(keyPair.getPublic()) // 공개키로 검증
                    .build()
                    .parseClaimsJws(token);

            // 만료 날짜 검사
            return !claims.getBody().getExpiration().before(new Date());
        } catch (io.jsonwebtoken.security.SignatureException ex) {
            // 서명 검증 실패
            return false;
        } catch (Exception e) {
            // 다른 모든 예외 처리
            return false;
        }
    }

}
