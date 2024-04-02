package com.example.web1.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web1.dto.SampleDto;

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
    public void basic(Model model) {
        log.info("/sample/basic 요청");

        model.addAttribute("name", "홍길동");

        // SampleDto sampleDto = new SampleDto();
        // sampleDto.setFirst("first");
        // sampleDto.setId(1L);
        // sampleDto.setLast("last");
        // sampleDto.setRegTime(LocalDateTime.now());

        // lombok Builder 패턴 사용 (객체 생성을 조금 더 쉽게 하는 법)
        SampleDto sampleDto = SampleDto.builder()
                .first("first")
                .id(1L)
                .last("last")
                .regTime(LocalDateTime.now())
                .build();

        model.addAttribute("dto", sampleDto);

        List<SampleDto> list = new ArrayList<>();
        for (Long i = 1L; i < 21; i++) {
            SampleDto dto = SampleDto.builder()
                    .first("first" + i)
                    .id(i)
                    .last("last" + i)
                    .regTime(LocalDateTime.now())
                    .build();

            list.add(dto);
        }
        model.addAttribute("list", list);

        model.addAttribute("now", new Date());
        model.addAttribute("price", 123456789);
        model.addAttribute("title", "This is a just sample");
        model.addAttribute("option", Arrays.asList("AAAA", "BBBB", "CCCC", "DDDD"));

    }

    // http://localhost:8080/sample/ex1 요청
    @GetMapping("/ex1")
    public void ex1(Model model) {
        log.info("/sample/ex1 요청");
        model.addAttribute("result", "SUCCESS");
    }

    @GetMapping("/ex2")
    public String ex2() {
        log.info("/sample/ex2 요청");
        // return "/board/create";
        return "/index";
    }

    @GetMapping("/ex3")
    public void ex3() {
        log.info("/sample/ex3 요청");
    }

    @GetMapping("/ex4")
    public void ex4(String param1, String param2, Model model) {
        log.info("/sample/ex4 요청");
        log.info("param1 {}, param2", param1, param2);
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);
    }

    @GetMapping("/ex5")
    public void ex5() {
        log.info("/sample/ex5 요청");
    }

    @GetMapping("/ex6")
    public void ex6() {
        log.info("/sample/ex6 요청");
    }

}
