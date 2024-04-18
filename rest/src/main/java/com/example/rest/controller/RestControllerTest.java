package com.example.rest.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.rest.dto.SampleDto;

import javassist.tools.reflect.Sample;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/*  컨트롤러 사용방식 2가지
 *  1. @Controller : 메소드가 끝나고 찾는 대상은 템플릿임
 *                   - @ResponseBody
 *  2. @RestController : 데이터 자체 리턴 가능
 *                       객체 → json 변환하는 컨버터 필요
 *                       - @PathVariable : path 에 담은 변수 받기
 * 
 */

@RestController
public class RestControllerTest {
    @GetMapping("/hello")
    public String getHello() {
        return "Hello World";
    }

    @GetMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE)
    public SampleDto getSample() {
        SampleDto dto = new SampleDto();
        dto.setMno(1L);
        dto.setFirstName("홍");
        dto.setLastName("길동");
        return dto;
    }

    @GetMapping(value = "/sample2", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SampleDto> getSample2() {

        List<SampleDto> list = new ArrayList<>();

        LongStream.rangeClosed(1, 10).forEach(i -> {
            SampleDto dto = new SampleDto();
            dto.setMno(i);
            dto.setFirstName("홍");
            dto.setLastName("길동");
            list.add(dto);
        });

        return list;
    }

    // 데이터 + 상태코드 (Http 상태코드 - 200, 500, 404)
    // ResponseEntity 객체
    // check?height=150&weight=45
    @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SampleDto> getCheck(double height, double weight) {
        SampleDto dto = new SampleDto();
        dto.setMno(1L);
        dto.setFirstName(String.valueOf(height));
        dto.setLastName(String.valueOf(weight));

        if (height < 150) {
            return new ResponseEntity<SampleDto>(dto, HttpStatus.BAD_REQUEST);
        } else {
            // 200 : OK
            return new ResponseEntity<SampleDto>(dto, HttpStatus.OK);
        }
    }

    // /product/bags/1234
    // /product?category=bags&pid=1234
    @GetMapping("/product/{cat}/{pid}")
    public String[] getMethodName(@PathVariable("cat") String cat, @PathVariable("pid") String pid) {
        return new String[] {
                "category : " + cat,
                "productId : " + pid
        };
    }

}