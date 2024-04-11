package com.example.todo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.todo.dto.TodoDto;
import com.example.todo.service.TodoService;
import com.example.todo.service.TodoServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoService service;

    // 멤버변수 초기화 1) 생성자 2) setter

    // / 로 접속 시
    @GetMapping("/list")
    public String list(Model model) {
        log.info("list 요청 ");

        List<TodoDto> list = service.getList();
        model.addAttribute("list", list);

        return "/todo/list";
    }

    // /todo/create
    @GetMapping("/create")
    public void getCreate() {
        log.info("create 템플릿 요청");
    }

    // 수정의 의미가 있는 것은 post로 보냄
    @PostMapping("/create")
    public String postCreate(TodoDto dto, RedirectAttributes rttr) {
        TodoDto result = service.create(dto);

        rttr.addFlashAttribute("msg", result.getId());
        return "redirect:/todo/list";
    }

    // todo/read?id=11 요청 처리 컨트롤러 생성
    @GetMapping("/read")
    public void getRead(@RequestParam Long id, Model model) {
        log.info("read 요청{}", id);

        TodoDto todo = service.getTodo(id);
        model.addAttribute("todo", todo);
    }

    // /todo/done 완료 목록 처리
    @GetMapping("/done")
    public void getDone(Model model) {
        log.info("완료 목록 요청 ");

        List<TodoDto> list = service.getCompletedList();
        model.addAttribute("list", list);
    }

    // /todo/update
    @PostMapping("/update")
    public String postUpdate(Long id, RedirectAttributes rttr) {
        // id 값 받기
        Long id2 = service.todoUpdate(id);

        rttr.addAttribute("id", id2);
        // read
        return "redirect:/todo/read";
    }

    @PostMapping("/delete")
    public String postDelete(@RequestParam Long id) {
        service.todoDelete(id);
        return "redirect:/todo/list";
    }

}