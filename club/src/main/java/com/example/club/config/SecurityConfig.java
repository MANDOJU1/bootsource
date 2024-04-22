package com.example.club.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    // Bean : 객체 생성해서 해줘
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.build();
    }

    // 암호화 (encode), 비밀번호 입력값 검증 (matches) : PasswordEncoder
    // 단방향 암호화 : 암호화만 가능하고 복호화는 불가능 (암호화된 비밀번호에서 원래 비밀번호 유추 x - 잊어버리면 새 비밀번호 생성해야함)
    @Bean // 객체 생성
    PasswordEncoder PasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
