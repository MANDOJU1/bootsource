package com.example.jpa.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class HomeController {
    // memo/home 보여주기
    @GetMapping("/")
    public String home() {
        log.info("home 요청");
        return "/memo/home";
    }
}
