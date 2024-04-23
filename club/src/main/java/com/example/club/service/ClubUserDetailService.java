package com.example.club.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.club.constant.ClubMemberRole;
import com.example.club.dto.ClubAuthMemberDto;
import com.example.club.dto.ClubMemberDto;
import com.example.club.entity.ClubMember;
import com.example.club.reposirtoy.ClubMemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class ClubUserDetailService implements UserDetailsService, ClubMemberService {

    private final ClubMemberRepository clubMemberRepository;

    private final PasswordEncoder passwordEncoder;

    // UserDetails ←--- User ←--- CustomUser
    // user or customuser 로 리턴해야함

    // loadUserByUsername() : 로그인 담당 메서드 (정해져있음)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인 담당 메서드
        // username : 회원 아이디
        log.info("로그인 요청 {}", username);

        Optional<ClubMember> result = clubMemberRepository.findByEmailAndFromSocial(username, false);

        if (!result.isPresent())
            throw new UsernameNotFoundException("이메일 혹은 social 확인");

        ClubMember clubMember = result.get();
        log.info("==================");
        log.info(clubMember);
        log.info("==================");

        // entity → dto
        ClubAuthMemberDto clubAuthMemberDto = new ClubAuthMemberDto(clubMember.getEmail(), clubMember.getPassword(),
                clubMember.isFromSocial(), clubMember.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet()));
        clubAuthMemberDto.setName(clubMember.getName());

        return clubAuthMemberDto;
    }

    @Override
    public String register(ClubMemberDto member) {
        // dto → entity 변경
        // dto 원본 비밀번호 ⇒ 암호화
        ClubMember clubMember = ClubMember.builder()
                .email(member.getEmail())
                .name(member.getName())
                .fromSocial(member.isFromSocial())
                .password(passwordEncoder.encode(member.getPassword()))
                .build();

        // role 부여
        clubMember.addMemberRole(ClubMemberRole.USER);

        return clubMemberRepository.save(clubMember).getEmail();

    }

}
