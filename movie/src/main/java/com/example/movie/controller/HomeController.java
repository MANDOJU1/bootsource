package com.example.movie.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.movie.dto.PageRequestDto;

@Log4j2
@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome() {
        log.info("home 요청");
        return "redirect:/movie/list";
    }

    @GetMapping("/access-denied")
    public void getAccess(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("접근 제한");
    }

    @GetMapping("/error")
    public String getError(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("404");

        // 스프링에서 만들어놓은 "/except/url404" 이것만 맞추면 error.html 불러옴
        // 형식, 경로 정해져 있기에 맞춰야함
        return "/except/url404";
    }

    @ResponseBody
    @GetMapping("/auth")
    public Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        return authentication;
    }

}