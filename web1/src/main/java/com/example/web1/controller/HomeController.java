package com.example.web1.controller;

import java.lang.ProcessBuilder.Redirect;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@Controller
public class HomeController {
    // *Error : TemplateInputException: Error resolving template [], template might
    // not exist or might not be accessible by any of the configured Template
    // Resolvers

    // value : "제공할 서비스 주소", .GET : get 방식 요청
    // @RequestMapping(value = "/", method = RequestMethod.GET)
    @GetMapping("/") // RequestMapping 보다 좀 더 간단
    public String home() {
        // c.e.web1.controller.HomeController : home 요청
        // log = sout
        log.info("home 요청");

        // templates 아래 경로부터 시작, 확장자 빼고 파일명만
        return "index";
    }

    // RedirectAttributes : redirect 시 데이터 전달
    // rttr.addAttribute("이름", 값); : 파라메터로 전달
    // rttr.addFlashAttribute("이름", 값); session 을 이용해서 저장

    @GetMapping("/ex3")
    public String ex3(RedirectAttributes rttr) {
        log.info("/ex3 요청");
        // response.sendRedirect("/qList.do")
        // path +="?bno="+bno;
        // return "redirect:/";

        // rttr.addAttribute("bno", 15); // http://localhost:8080/sample/basic?bno=15

        // Session 을 이용해서 저장
        rttr.addFlashAttribute("bno", 15);

        // @GetMapping("/") 여기로 이동
        // 경로 지정 (다른 컨트롤러에 있는 경로 포함)
        return "redirect:/sample/basic";
    }

    // *Error : IllegalStateException: Ambiguous mapping.
    // @GetMapping("/ex3") 전체 컨트롤러에서 동일한 매핑 방식, 경로 지정 시
    // @GetMapping("/ex3")
    // public void ex4() {
    // log.info("/ex3 요청");
    // }
}