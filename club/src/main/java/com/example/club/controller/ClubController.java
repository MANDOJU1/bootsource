package com.example.club.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@RequestMapping("/club")
@Controller
public class ClubController {
    @GetMapping("/manager")
    public void getManager() {
        log.info("manager 요청");
    }

    @GetMapping("/member")
    public void getMember() {
        log.info("member 요청");
    }

    @GetMapping("/admin")
    public void getAdmin() {
        log.info("admin 요청");
    }

}
