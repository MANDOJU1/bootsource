package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/sample")
public class SampleController {
    // String, void 형태의 메소드 작성
    // 404 : 경로 없음(Controller에 매핑 주소 확인)
    // 500 : 서버 코드오류 (Error resolving template [sample/basic], template might not
    // exist or might not be accessible by any of the configured Template Resolvers)
    // templates 폴더를 확인
    // 400 : Bad Request, status=400
    // Failed to convert value of type 'java.lang.String' to required type 'int'
    // Internal Server Error, status=500

    // 리턴타입 결정 (void, String)
    // void : 경로와 일치하는 곳에 템플릿이 존재할 때
    // http://localhost:8080/board/create (==> create.html 템플릿 만들기)
    // http://localhost:8080/board/user/create
    // http://localhost:8080/board/member/create

    // String : 경로와 일치하는 곳에 템플릿이 없을 때 (템플릿의 위치와 관계없이 자유롭게 지정 가능)

    // http://localhost:8080/sample/basic 요청 => GET방식
    @GetMapping("/basic") // 위에 @RequestMapping("/sample") 사용하면 /sample 생략 가능
    public void basic() {
        log.info("/sample/basic 요청");
    }

    // http://localhost:8080/sample/ex1 요청
    @GetMapping("/ex1")
    public void ex1() {
        log.info("/sample/ex1 요청");
    }

    @GetMapping("/ex2")
    public String ex2() {
        log.info("/sample/ex2 요청");
        // return "/board/create";
        return "/index";
    }

}
