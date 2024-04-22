package com.example.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityTest {

    // SecurityConfig 의 PasswordEncoder() 가 실행되면서 주입됨
    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 암호화(encode), 원 비밀번호와 암호화된 비밀번호의 매치 여부(matches) (를 확인하는 하는 메서드를 가지고
                                             // 있음)

    @Test
    public void testEncoder() {
        String password = "1111"; // 원비밀번호

        // 원 비밀번호를 암호화 시켜줌 (인코딩해줌)
        String encodePassword = passwordEncoder.encode(password);

        // password : 1111, encode password :
        // {bcrypt}$2a$10$AF3oMZPghkbCUUSop65HTu..NZms3rGYU2A/vyXO/nJGplSC6fDJ2
        // {bcrypt} : 암호화 알고리즘 중 하나 사용
        System.out.println("password : " + password + ", encode password : " + encodePassword);

        // matches(원비밀번호, 암호화된 비밀번호)
        // true
        // (1111, {bcrypt}$2a$10$AF3oMZPghkbCUUSop65HTu..NZms3rGYU2A/vyXO/nJGplSC6fDJ2{bcrypt})
        System.out.println(passwordEncoder.matches(password, encodePassword));
    }
}
