package com.example.book.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.book.dto.BookDto;
import com.example.book.dto.PageRequestDto;
import com.example.book.dto.PageResultDto;
import com.example.book.entity.Book;
import com.example.book.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@RestController
public class BookRestController {

    private final BookService service;

    // /pages/2 => 10개 데이터 가져오기
    @GetMapping("/pages/{page}")
    public ResponseEntity<PageResultDto<BookDto, Book>> list(@PathVariable("page") int page) {
        log.info("list 요청");

        PageRequestDto requestDto = new PageRequestDto();
        requestDto.setPage(page);
        PageResultDto<BookDto, Book> result = service.getList(requestDto);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    // /read/15
    @GetMapping("/read/{id}")
    public ResponseEntity<BookDto> read(@PathVariable("id") Long id) {
        log.info("read 요청", id);

        BookDto bookDto = service.getRow(id);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    // @RequestBody : json 으로 넘어오는 데이터를 객체 바인딩
    @PostMapping("/book/new")
    public ResponseEntity<String> postCreate(@RequestBody @Valid BookDto dto) {
        log.info("book post 요청 {}", dto);

        // insert 작성
        Long id = service.bookCreate(dto);

        // Vaild 검증 성공한 경우
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    // /modify/3 + 데이터
    @PutMapping("/modify/{id}")
    public ResponseEntity<String> postModify(@RequestBody BookDto updateDto, @PathVariable("id") Long id) {
        log.info("업데이트 요청 {}", updateDto);

        service.bookUpdate(updateDto);

        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> postDelete(@PathVariable("id") Long id) {
        log.info("도서 삭제 요청 {}", id);
        service.bookDelete(id);

        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    // Valid 유효성 검증 실패한 겨우 - Exception 나면 Handler 응답
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}