package com.example.jpa.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.jpa.dto.MemoDto;
import com.example.jpa.service.MemoServiceImpl;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Log4j2
@Controller
@RequestMapping("/memo")
public class MemoController {

    // MemoServiceImpl에서 Service - 생성해 놓은거 주입 @RequiredArgsConstructor
    private final MemoServiceImpl service;

    // /memo/list + GET
    @GetMapping("/list")
    public void list(Model model) {
        log.info("list 요청");

        List<MemoDto> list = service.getMemoList();

        // list 를 list.html 보여주고 싶다면?
        model.addAttribute("list", list);
    }

    // /memo/read?mno=3 + GET
    @GetMapping({ "/read", "/modify" })
    public void read(@RequestParam Long mno, Model model) {
        log.info("read form 요청");

        MemoDto mDto = service.getMemo(mno);
        model.addAttribute("mDto", mDto);

        // 템플릿 찾기
        // /memo/read => templetes 폴더 아래 /memo/read.html
        // /memo/modify => templetes 폴더 아래 /memo/modify.html
    }

    // /memo/modify?mno=3 + GET : 수정을 위해 화면 보여주기 요청
    // /memo/modify + POST : 실제 수정 요청
    @PostMapping("/modify")
    public String modifyPost(MemoDto mDto, RedirectAttributes rttr) {
        log.info("modify 요청" + mDto);
        MemoDto updateDto = service.updteMemo(mDto);

        // 수정 완료 시 /memo/read?mno=? 이동
        // RedirectAttributes : 뒤에 주소줄 따라가게 하기 위한 코드
        rttr.addAttribute("mno", updateDto.getMno());
        return "redirect:/memo/read";
    }

    // /memo/delete?mno=3 + GET : 삭제 요청
    @GetMapping("/delete")
    public String deleteGet(@RequestParam Long mno) {
        log.info("메모 삭제 요청", mno);
        service.deleteMemo(mno);

        // 삭제 성공 시 리스트
        return "redirect:/memo/list";
    }

    // /memo/create + GET : 입력을 위해 화면 보여주기
    @GetMapping("/create")
    // create.html 에서 ${mDto}를 사용하려면 @ModelAttribute 사용하여 가져올 수 있도록 해야함 / 아니면
    // ${memoDto.mno}를 써야함
    public void getCreate(@ModelAttribute("mDto") MemoDto mDto) {
        log.info("메모 입력 폼 요청");
    }

    // /memo/create + POST : 실제 입력 요청
    @PostMapping("/create")
    public String postCreate(@Valid @ModelAttribute("mDto") MemoDto mDto, BindingResult result,
            RedirectAttributes rttr) {
        log.info("create post 요청 {}", mDto);

        if (result.hasErrors()) {
            return "/memo/create";

        }

        service.insertMemo(mDto);

        rttr.addFlashAttribute("result", "SUCCESS");

        return "redirect:/memo/list";
    }

}
