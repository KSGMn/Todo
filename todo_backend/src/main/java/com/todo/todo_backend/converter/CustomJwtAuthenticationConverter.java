package com.todo.todo_backend.converter;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.todo.todo_backend.auth.service.JwtTokenService;
import com.todo.todo_backend.common.exception.CustomExceptions;

@Component
public class CustomJwtAuthenticationConverter extends SimpleJwtAuthenticationConverter {

    @Autowired
    @Lazy
    private JwtTokenService jwtTokenService;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt, Collection<GrantedAuthority> authorities) {

        if (jwt == null) {
            throw new CustomExceptions.UnauthorizedException("Token is missing");
        }

        if (jwtTokenService.isTokenBlacklisted(jwt.getTokenValue())) {
            throw new CustomExceptions.UnauthorizedException("Token is blacklisted");
        }

        if (!jwtTokenService.validateAccessToken(jwt.getTokenValue())) {
            throw new CustomExceptions.UnauthorizedException("Token validation failed");
        }

        return new JwtAuthenticationToken(jwt, authorities);
    }
}
