package com.example.movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.movie.handler.CustomAccessDeniedHandler;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/assets/**", "/css/**", "/js/**", "/auth").permitAll()
                .requestMatchers("/movie/list", "/movie/read").permitAll()
                .requestMatchers("/movie/list", "/movie/modify").hasRole("ADMIN")
                // 사진 업로드
                // 이미지는 보이지 않음 → 해결 방안 list.html 에 /upload/display를 열어야 이미지가 보임
                .requestMatchers("/upload/display").permitAll()
                // 리뷰가 보이지 않음 → 해결 방안 reviews 를 모두 열어야 리뷰가 보임
                .requestMatchers("/reviews/**").permitAll()
                .requestMatchers("/member/register").permitAll()
                .anyRequest().authenticated());
        // login 페이지는 /member/login 경로요청 해야함
        // login 성공 후 이동경로는 시작했던 곳으로 가는게 기본
        http.formLogin(login -> login.loginPage("/member/login").permitAll()
                .defaultSuccessUrl("/movie/list", true));
        http.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/"));

        // csrf 를 막으면 get 을 제외한 모든 방식은 csrf 토큰이 필요함
        // → thymeleaf 에서는 form:action 을 삽입하면 자동으로 만들어 준다
        // → 403 발생 시 csrf 토큰이 포함되었는지 1차 확인
        // → 입력값 2차 확인
        // csrf 를 열면 필터 비활성화 (잠시 시큐리티 비활성화)
        // http.csrf(csrf -> csrf.disable());

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        // 403(접근제한) - 정적 페이지와 연결
        // http.exceptionHandling(exception ->
        // exception.accessDeniedPage("/access-denied.html"));

        http.exceptionHandling(exception -> exception.accessDeniedHandler(customAccessDeniedHandler()));

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}