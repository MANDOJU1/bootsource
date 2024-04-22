package com.example.club.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ClubUserDetailService implements UserDetailsService {

    // UserDetails ←--- User ←--- CustomUser
    // user or customuser 로 리턴해야함

    // loadUserByUsername() : 로그인 담당 메서드 (정해져있음)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인 담당 메서드
        // username : 회원 아이디
        log.info("로그인 요청 {}", username);
        return null;
    }

}
