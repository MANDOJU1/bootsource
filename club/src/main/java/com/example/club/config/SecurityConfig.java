package com.example.club.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.club.handler.ClubLoginSuccessHandler;

@EnableMethodSecurity // @PreAuthorize 활성화
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    // Bean : 객체 생성해서 해줘
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
        http
                // 접근 제한 경로를 전부 작성
                // .authorizeHttpRequests(authorize -> authorize
                // .requestMatchers("/", "/auth", "/static/**", "/img/*").permitAll()
                // .requestMatchers("/club/member").hasAnyRole("USER", "MANAGER", "ADMIN")
                // .requestMatchers("/club/manager").hasAnyRole("MANAGER")
                // .requestMatchers("/club/admin").hasAnyRole("ADMIN"))

                // 위에 작성을 간단히 하는 법 => Controller에 가서 쓰기 @PreAuthorize , config 파일에
                // @EnableMethodSecurity 추가
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/static/**", "/img/*").permitAll()
                        .anyRequest().permitAll())
                .formLogin(login -> login
                        .loginPage("/club/member/login").permitAll()
                        .successHandler(clubLoginSuccessHandler()))
                .oauth2Login(login -> login.successHandler(clubLoginSuccessHandler()))
                .rememberMe(remember -> remember.rememberMeServices(rememberMeServices))
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/club/member/logout"))
                        .logoutSuccessUrl("/"));
        return http.build();
    }

    // 자동 로그인 처리 - 1) 쿠키 이용 2) 데이터 베이스 이용
    // 브라우저를 다 내리고 켜도 사이트에 들어가면 로그인 되어있음
    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("mykey", userDetailsService,
                encodingAlgorithm);
        rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7); // 쿠키 만료 시간
        return rememberMeServices;
    }

    // 암호화 (encode), 비밀번호 입력값 검증 (matches) : PasswordEncoder
    // 단방향 암호화 : 암호화만 가능하고 복호화는 불가능 (암호화된 비밀번호에서 원래 비밀번호 유추 x - 잊어버리면 새 비밀번호 생성해야함)
    @Bean // 객체 생성
    PasswordEncoder PasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ClubLoginSuccessHandler clubLoginSuccessHandler() {
        return new ClubLoginSuccessHandler();
    }
}
