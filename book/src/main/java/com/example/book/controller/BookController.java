package com.example.book.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDto;
import com.example.book.service.BookService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RequestMapping("/book")
@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping("/list")
    public void getList(Model model) {
        log.info("list 요청");
        List<BookDto> list = service.getList();
        model.addAttribute("list", list);
    }

    // org.attoparser.ParseException: Exception evaluating SpringEL expression:
    // "bookDto.title" 오류가 남
    // 해결방법 : Post에서 받은 BookDto dto 같이 써줘야함
    @GetMapping("/create")
    public void getCreate(BookDto dto, Model model) {
        log.info("create 요청");

        // 테이블에있는 카테고리 명 보여주기
        model.addAttribute("categories", service.categoryNameList());

    }

    // 등록을 누르면 유효성 검사를 하기 때문에 POST로 받기!
    // BookDto 클래스에서 무엇을 검증할건지 검증조건을 넣어야 함
    // Create 에서 값을 가져올 때 bookdto.~ 라고 써야함 하지만 @ModelAttribute("dto") 사용하게 되면 dto.~
    // 사용가능함 (이름이 길 때 사용하면 좋음)
    // 또는 개별 변수를 받을 때 사용해야함
    @PostMapping("/create")
    public String postCreate(@Valid BookDto dto, BindingResult result, RedirectAttributes rttr, Model model) {

        log.info("book post 요청 {} ", dto);

        if (result.hasErrors()) {
            model.addAttribute("categories", service.categoryNameList());
            return "/book/create";
        }

        // insert 작성
        Long id = service.bookCreate(dto);
        rttr.addFlashAttribute("result", id);
        return "redirect:/book/list";
    }

    @GetMapping(value = { "/read", "/modify" })
    public void getRead(@RequestParam Long id, Model model) {
        log.info("read or modify요청");

        model.addAttribute("bookDto", service.getRow(id));
    }

    @PostMapping("/modify")
    public String postModify(BookDto updateDto, RedirectAttributes rttr) {
        log.info("업데이트 요청 {}", updateDto);
        Long id = service.bookUpdate(updateDto);

        // 조회화면으로 이동
        // 주소줄에 따라가게하는것 id=?
        rttr.addAttribute("id", id);
        // redirect 를 사용하면 @GET 값이 넘어감
        return "redirect:/book/read";
    }

    @PostMapping("/delete")
    public String postDelete(Long id) {
        log.info("도서 삭제 요청 {}", id);

        service.bookDelete(id);

        return "redirect:/book/list";
    }

}
