package com.example.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class HomeController {
    // / 로 접속 시
    @GetMapping("/")
    public String Home() {
        log.info("list 요청 ");

        // redirect : localhost:8080을 치면 /todo/list 로 가게 해줘
        return "redirect:/todo/list";
    }
}
