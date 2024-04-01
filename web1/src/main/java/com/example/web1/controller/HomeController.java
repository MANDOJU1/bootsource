package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class HomeController {

    // get 방식 : 기본방식
    // post 방식 : form 안에서 method에서 사용

    // @RequestMapping(value = "/", method = RequestMethod.GET)
    @GetMapping("/") // @RequestMapping (GET) 대신 사용
    public String home() {
        // c.e.web1.controller.HomeController : home 요청
        log.info("home 요청"); // == sout

        // templates 아래 경로부터 시작, 확장자 빼고 파일명
        return "index";
    }

}
