package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.dto.BoardDto;
import com.example.board.dto.PageRequestDto;
import com.example.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService service;

    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        log.info("list 요청");

        model.addAttribute("result", service.getList(requestDto));
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(@RequestParam Long bno, Model model) {
        log.info("read or modify 요청");

        model.addAttribute("dto", service.getRow(bno));
    }

    @PostMapping("/modify")
    public String postModify(BoardDto dto, RedirectAttributes rttr) {
        log.info("modify 요청", dto);

        service.modify(dto);

        rttr.addAttribute("bno", dto.getBno());
        return "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String postRemove(@RequestParam Long bno, RedirectAttributes rttr) {
        log.info("remove 요청", bno);

        service.removeWithReplies(bno);

        return "redirect:/board/list";
    }

}
