package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration  // 스프링 설정 클래스 지정
@EnableWebSecurity  // 스프링 보안 활성화
public class SecurityConfig {

    @Bean  // 보안 필터 체인 설정
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 보안 헤더 설정
            .headers(headers -> 
                headers.addHeaderWriter((request, response) -> {
                    response.setHeader("X-XSS-Protection", "1; mode=block");  // XSS 보호
                })
            )

            // CSRF 기본 설정
            .csrf(withDefaults())

            // 세션 관리 설정
            .sessionManagement(session -> 
                session
                    .invalidSessionUrl("/session-expired")  // 세션 만료 시 이동 URL
                    .maximumSessions(1)                     // 동시에 1개 세션만 허용
                    .maxSessionsPreventsLogin(true)         // 기존 세션 있을 경우 새 로그인 차단
            );

        return http.build();
    }

    @Bean  // 비밀번호 암호화
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
