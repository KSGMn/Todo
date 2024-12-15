package com.todo.todo_backend.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity // Spring Security를 활성화하기 위해 추가
@EnableMethodSecurity(securedEnabled = true) // 메서드 수준의 보안을 활성화하기 위해 추가
public class SecurityConfig {

    // @Autowired
    // private customJwtAuthenticationConverter customJwtAuthenticationConverter;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource()))
                .csrf(CsrfConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/auth/**", "/api/v1/todos", "/api/v1/todos/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**", "/")
                        .permitAll() // CORS 사전 요청 Preflight request)접근 허용
                        // .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                // .oauth2ResourceServer(
                // auth2 -> auth2.jwt(jwt -> jwt.jwtAuthenticationConverter(
                // customJwtAuthenticationConverter)))
                // .exceptionHandling(exception -> exception
                // .authenticationEntryPoint(new FailedAuthenticationEntryPoint())
                // .accessDeniedHandler(new CustomAccessDeniedHandler()))
                // OAuth2 리소스 서버로 JWT 사용
                .headers(header -> {
                    header.frameOptions().sameOrigin(); // 클랙지킹 방지를 위해 동일 출처만 허용
                }).logout(logout -> logout.disable());// 기본 스프링 securty 로그아웃을 비활성화해주자 그렇지않으면 404에러가 난다
        // .oauth2Login(oauth2 -> oauth2
        // .authorizationEndpoint(
        // endpoint -> endpoint.baseUri("/api/v1/auth/oauth2"))
        // .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
        // .userInfoEndpoint(endpoint -> endpoint.userService(oAuth2UserService))
        // .successHandler(oAuth2SuccessHandler))
        // .exceptionHandling(exceptionHandling -> exceptionHandling
        // .authenticationEntryPoint(new FailedAuthenticationEntryPoint()))
        // .addFilterBefore(jwtAuthenticationFilter,
        // UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    // 인증되지 않은 사용자가 접근할때
    class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException authException) throws IOException, ServletException {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"code\": \"UNAUTHORIZED\", \"message\": \"Authentication required.\"}");

        }
    }

    // 인증은 되었지만 권한이 없을때
    class CustomAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response,
                AccessDeniedException accessDeniedException) throws IOException {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"code\": \"FORBIDDEN\", \"message\": \"No Permission.\"}");
        }
    }
}
