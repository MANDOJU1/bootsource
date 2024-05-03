package com.example.movie.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.movie.dto.AuthMemberDto;
import com.example.movie.dto.MemberDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PasswordChangeDto;
import com.example.movie.entity.Member;
import com.example.movie.service.MovieUserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/member")
@RequiredArgsConstructor
@Log4j2
@Controller
public class memberController {

    private final MovieUserService movieUserService;

    @GetMapping("/login")
    // @ModelAttribute("requestDto") PageRequestDto pageRequestDto 왜 계속 따라감? :
    // header를 공통으로 공유하고 있기 때문에 써야 함
    public void getLogin(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("로그인 폼 요청");
    }

    // /profile → profile.html 보여주기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public void getProfile(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("프로필 폼 요청");

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit")
    public String getEdit(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        return "/member/edit-profile";
    }

    // /edit/nickname
    @PostMapping("/edit/nickname")
    public String postNickname(MemberDto memberDto, HttpSession session) {

        movieUserService.nickNameUpdate(memberDto);
        // SecurityContent 안에 저장된 Authentication 변경되지 않음 (== 로그아웃을 해야 변경된 닉네임을 볼 수 있음)
        // 1) 현재 세션 제거(HttpSession session 추가) → 재로그인
        // session.invalidate();

        // 2) Authentication 값 업데이트
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        AuthMemberDto authMemberDto = (AuthMemberDto) authentication.getPrincipal();
        authMemberDto.getMemberDto().setNickname(memberDto.getNickname());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/member/profile";
    }

    // /edit/password
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/password")
    public String postPassword(PasswordChangeDto pDto, HttpSession session, RedirectAttributes rttr) {

        // 현재 비번이 틀리면 /member/edit
        try {
            movieUserService.passwordUpdate(pDto);
        } catch (Exception e) {
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/edit";
        }
        session.invalidate();
        return "redirect:/member/login";
    }

    @GetMapping("/register")
    public void getRegister(MemberDto memberDto, @ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("회원가입 폼 요청");
    }

    @PostMapping("/register")
    public String postRegister(@Valid MemberDto dto, BindingResult result, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("회원가입 요청 {}", dto);

        if (result.hasErrors()) {
            return "/member/register";
        }

        String email = "";
        try {
            email = movieUserService.register(dto);

        } catch (Exception e) {
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/register";
        }

        rttr.addFlashAttribute("email", email);
        return "redirect:/member/login";
    }

    @GetMapping("/leave")
    public void getLeaveForm(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("회원탈퇴");

    }

    @PostMapping("/leave")
    public String postLeave(MemberDto leavMemberDto, RedirectAttributes rttr, HttpSession session) {
        log.info("회원탈퇴 {}", leavMemberDto);

        try {
            movieUserService.leave(leavMemberDto);

        } catch (Exception e) {
            rttr.addFlashAttribute("error", "이메일이나 비밀번호를 확인해 주세요");
        }
        session.invalidate();

        return "/redirect:/";
    }

}
