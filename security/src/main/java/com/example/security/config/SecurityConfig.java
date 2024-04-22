package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity // 웹에서 security 적용 가능
@Configuration // = @Component(@Controller, @Service) : 환경설정 담당 객체 생성 선언
public class SecurityConfig {

    // 접근 제한 개념

    @Bean
    SecurityFilterChain securityFilterChaniChain(HttpSecurity http) throws Exception {
        http
                // 요청 확인
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/security/guest", "/auth").permitAll() // 8080에 대한 접근 열기 (permition all
                                                                                      // 모두 열기)
                        // 해당 경로로 접근 시 USER, ADMIN 으로 인가 받았는지 확인
                        .requestMatchers("/security/member").hasRole("USER")
                        .requestMatchers("/security/admin").hasRole("ADMIN"))
                // 인증 처리 (웹에서는 대부분 폼 로그인 작업)
                // .formLogin(Customizer.withDefaults()); // 이외 접근 시 default 로그인 페이지 보여주기
                .formLogin(login -> login
                        .loginPage("/member/login").permitAll()) // 커스텀 로그인 페이지 호출
                // .usernameParameter("userid") => username 요소 이름 변경 시
                // .passwordParameter("pwd") => password 요소 이름 변경 시
                // .successForwardUrl("")) => 로그인 성공 후 가야할 곳 지정
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 커스텀 로그아웃 페이지
                        .logoutSuccessUrl("/")); // default 로그인 페이지임

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // 비밀번호 암호화
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // 임시 - 데이터베이스에 인증을 요청하는 객체
    // InMemoryUserDetailsManager - 메모리에 등록해 놓고 임시로 사용
    @Bean
    UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user1")
                .password("{bcrypt}$2a$10$UZk3kUewXSRw5qfkdoksvu5QI9BSiPEBjfQh7Cr3LW.ei4vUGA3iO")
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin1")
                .password("{bcrypt}$2a$10$UZk3kUewXSRw5qfkdoksvu5QI9BSiPEBjfQh7Cr3LW.ei4vUGA3iO")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}