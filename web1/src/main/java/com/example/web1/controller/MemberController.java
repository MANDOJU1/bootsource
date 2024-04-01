package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web1.dto.MemberDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequestMapping("/member")
public class MemberController {
    @GetMapping("/login")
    public void login() {
        log.info("로그인 페이지 요청");
    }

    // @PostMapping("/login")
    // public void loginPost(String email, String name) {
    // log.info("로그인 정보 가져오기");
    // log.info("email {}", email);
    // log.info("name {}", name);
    // }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute("mDto") MemberDto mDto, @ModelAttribute("page") int page, Model model) {
        log.info("/member/login post 요청");
        log.info("email {} ", mDto.getEmail());
        log.info("name {}", mDto.getName());
        log.info("page {}", page);

        // model.addAttribute("page", page); == @ModelAttribute("page")

        return "/member/info";
    }

    // 데이터 보내기
    // requset.setAtrribute("이름", 값) == Model

}
