package com.example.guestbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.dto.PageRequestDto;
import com.example.guestbook.dto.PageResultDto;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.service.GuestBookService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/guestbook")
public class GuestBookController {

    private final GuestBookService service;

    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        log.info("list 요청");

        // 페이지 나누기 전
        // model.addAttribute("list", service.getList());

        PageResultDto<GuestBookDto, GuestBook> result = service.getList(requestDto);

        model.addAttribute("result", result);
    }

    @GetMapping({ "/read", "modify" })
    public void getRead(@RequestParam Long gno, Model model, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("read or modify 요청 ");

        model.addAttribute("bookDto", service.getRow(gno));

    }

    @PostMapping("/modify")
    public String postModify(GuestBookDto dto, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("modify 요청 {}", dto);
        service.bookUpdate(dto);

        rttr.addAttribute("gno", dto.getGno());
        // 페이지 나누기 정보
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/guestbook/read";
    }

    @PostMapping("/delete")
    public String postDelete(@RequestParam Long gno, @ModelAttribute("requestDto") PageRequestDto requestDto,
            RedirectAttributes rttr) {
        log.info("delete 요청", gno);

        service.bookDelete(gno);
        // 페이지 나누기 정보
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/guestbook/list";
    }

    @GetMapping("/create")
    public void getCreate(GuestBookDto dto, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("등록 폼 요청");

    }

    @PostMapping("/create")
    // @Valid GuestBookDto dto 뒤에 바로 BindingResult result 와야함 (순서 지키기!)
    public String postMethodName(@Valid GuestBookDto dto, BindingResult result,
            @ModelAttribute("requestDto") PageRequestDto requestDto, RedirectAttributes rttr) {
        log.info("등록 요청", dto);

        if (result.hasErrors()) {
            return "/guestbook/create";
        }

        Long gno = service.insert(dto);

        rttr.addFlashAttribute("msg", gno);
        // 페이지 나누기 정보
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/guestbook/list";
    }

}