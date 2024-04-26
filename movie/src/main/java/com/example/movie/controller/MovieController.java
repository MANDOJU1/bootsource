package com.example.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.movie.dto.MovieDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PageResultDto;
import com.example.movie.service.MovieService;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/movie")
@Controller
public class MovieController {

    private final MovieService service;

    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDto") PageRequestDto pageRequestDto, Model model) {
        log.info("list 요청");

        PageResultDto<MovieDto, Object[]> result = service.getList(pageRequestDto);

        model.addAttribute("result", result);
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(@RequestParam Long mno, Model model,
            @ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("영화 상세 정보 요청 {}", mno);

        model.addAttribute("dto", service.getRow(mno));
    }

    @PostMapping("/remove")
    public String getRemove(@RequestParam Long mno, @ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("영화 삭제 요청 {}", mno);

        service.movieRemove(mno);
        return "redirect:/movie/list";
    }

}