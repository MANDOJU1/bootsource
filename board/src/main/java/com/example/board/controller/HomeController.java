package com.example.board.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.dto.PageRequestDto;

@Log4j2
@Controller
public class HomeController {
    @GetMapping("/")
    public String home(RedirectAttributes rttr, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("home 요청");

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:board/list";
    }
}