package com.example.board.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.board.constant.MemberRole;
import com.example.board.dto.MemberAuthDto;
import com.example.board.dto.MemberDto;
import com.example.board.entity.Member;
import com.example.board.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class MemberDetailService implements UserDetailsService, MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // id == email == username
        Optional<Member> result = memberRepository.findById(username);

        if (!result.isPresent())
            throw new UsernameNotFoundException("이메일을 확인해 주세요");

        Member member = result.get();
        // entity → dto
        // 시큐리티 로그인 → 회원 정보 + 허가와 관련된 정보

        MemberDto memberDto = MemberDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .memberRole(member.getMemberRole())
                .build();

        // 여기서 담은 memberDto가 MemberAuthDto 에 가서 작동함
        return new MemberAuthDto(memberDto);

    }

    @Override
    public void register(MemberDto insertDto) {
        // 회원가입
        log.info("서비스 회원가입 요청 {} ", insertDto);

        try {
            // 중복 이메일 확인
            validateDuplicationMember(insertDto.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 이메일 넣으면 select → 중복된 이메일 존재 시 update구문 실행 / 없으면 insert구문 실행
        Member member = Member.builder()
                .email(insertDto.getEmail())
                .password(passwordEncoder.encode(insertDto.getPassword()))
                .name(insertDto.getName())
                .memberRole(MemberRole.MEMBER)
                .build();

        memberRepository.save(member);

    }

    private void validateDuplicationMember(String email) {
        Optional<Member> member = memberRepository.findById(email);
        if (member.isPresent())
            throw new IllegalStateException("이미 가입된 회원입니다");

    }

}
