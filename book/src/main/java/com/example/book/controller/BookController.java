package com.example.book.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequestMapping("/book")
public class BookController {

    @GetMapping("/list")
    public String getlist() {
        log.info("list 요청");
        return "/book/list";

    }

    @GetMapping("/modify")
    public String getModify() {
        log.info("modify 요청");
        return "/book/modify";
    }

    @GetMapping("/create")
    public String getCreate() {
        log.info("create 요청");
        return "/book/create";
    }

    @GetMapping("/read")
    public String getRead() {
        log.info("read 요청");
        return "/book/read";
    }
}
