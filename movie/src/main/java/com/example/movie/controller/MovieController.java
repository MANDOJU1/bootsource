package com.example.movie.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.movie.dto.MovieDto;
import com.example.movie.dto.MovieImageDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PageResultDto;
import com.example.movie.service.MovieService;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/modify")
    public String postModify(MovieDto movieDto, RedirectAttributes rttr, PageRequestDto pageRequestDto) {
        log.info("movie 수정 요청 {}", movieDto);

        Long mno = service.movieUpdate(movieDto);

        // addFlashAttribute : 새로고침하면 사라짐
        rttr.addFlashAttribute("msg", mno);
        rttr.addAttribute("mno", movieDto.getMno());

        rttr.addAttribute("page", pageRequestDto.getPage());
        rttr.addAttribute("size", pageRequestDto.getSize());
        rttr.addAttribute("type", pageRequestDto.getType());
        rttr.addAttribute("keyword", pageRequestDto.getKeyword());
        return "redirect:/movie/read";
    }

    @PostMapping("/remove")
    public String getRemove(@RequestParam Long mno, @ModelAttribute("requestDto") PageRequestDto pageRequestDto,
            RedirectAttributes rttr) {
        log.info("영화 삭제 요청 {}", mno);

        service.movieRemove(mno);

        rttr.addAttribute("page", pageRequestDto.getPage());
        rttr.addAttribute("size", pageRequestDto.getSize());
        rttr.addAttribute("type", pageRequestDto.getType());
        rttr.addAttribute("keyword", pageRequestDto.getKeyword());
        return "redirect:/movie/list";
    }

    @GetMapping("/register")
    public void getRegister(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("영화 등록 폼 요청 ");

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public String postRegister(MovieDto movieDto, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("영화 등록 {}", movieDto);

        // 서비스 호출
        Long mno = service.movieInsert(movieDto);

        // mno 넘기기
        rttr.addFlashAttribute("msg", mno);

        rttr.addAttribute("page", pageRequestDto.getPage());
        rttr.addAttribute("size", pageRequestDto.getSize());
        rttr.addAttribute("type", pageRequestDto.getType());
        rttr.addAttribute("keyword", pageRequestDto.getKeyword());

        return "redirect:/movie/list";
    }

}