package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web1.dto.AddDto;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequestMapping("/calc")
public class AddController {
    @GetMapping("/add")
    public void addGet() {
        log.info("/calc/add 요청");

    }

    // 사용자 입력 값 가져오기 (HttpServletRequest req, 파라메터)
    // 1. HttpServletRequest req 이용
    // 2. 파라메터 이용 (form 이름과 변수명 일치)
    // 3. DTO 이용
    // - post 방식 이후의 화면 컨트롤러 응답 후 보여지는 화면단에서 dto에 들어 이쓴 값을 사용 가능

    // 1번 방법
    // @PostMapping("/add")
    // public void addPost(HttpServletRequest req) {
    // log.info("/calc/add post 요청");
    // log.info("num1 {}", req.getParameter("num1"));
    // log.info("num2 {}", req.getParameter("num2"));
    // }

    // 2번 방법
    // @PostMapping("/add")
    // public void addPost(@RequestParam(value = "op1", defaultValue = "0") int
    // num1,
    // @RequestParam(value = "op2", defaultValue = "0") int num2) { // 타입변환을
    // // 알아서
    // // 해줌
    // log.info("/calc/add post 요청");
    // log.info("num1 {}", num1);
    // log.info("num2 {}", num2);
    // }

    // 3번 방법
    @PostMapping("/add")
    public void addPost(AddDto dto, Model model) {
        log.info("/calc/add post 요청");
        log.info("num1 {}", dto.getNum1());
        log.info("num2 {}", dto.getNum2());

        // dto.setResult(dto.getNum1() + dto.getNum2());
        model.addAttribute("result", dto.getNum1() + dto.getNum2());
    }

    @GetMapping("/rules")
    public void rulesGet() {
        log.info("/calc/rules 요청");
    }

    @PostMapping("/rules")
    public String postMethodName(AddDto addDto, @ModelAttribute("op") String op, Model model) {
        log.info("/calc/rules post 요청");
        int result = 0;
        switch (op) {
            case "+":
                result = addDto.getNum1() + addDto.getNum2();
                break;
            case "-":
                result = addDto.getNum1() - addDto.getNum2();
                break;
            case "*":
                result = addDto.getNum1() * addDto.getNum2();
                break;
            case "/":
                result = addDto.getNum1() / addDto.getNum2();
                break;
        }
        // model.addAttribute("result", result);
        addDto.setResult(result);
        return "/calc/result";
    }

}
