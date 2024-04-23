package com.example.board.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.dto.MemberDto;
import com.example.board.dto.PageRequestDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    @PreAuthorize("permitAll()")
    @GetMapping("/login")
    public void getLogin(@ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("로그인 폼 요청 ");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/register")
    public void getRegister() {
        log.info("회원가입 요청");
    }

    @PostMapping("/register")
    public String postRegister(MemberDto memberDto) {

        return "redirect:/member/login";
    }

}
