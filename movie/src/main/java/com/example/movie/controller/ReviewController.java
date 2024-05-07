package com.example.movie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movie.dto.ReviewDto;
import com.example.movie.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Log4j2
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // /reviews/3/all - mno
    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDto>> getReviews(@PathVariable("mno") Long mno) {

        return new ResponseEntity<>(reviewService.getListOfMovie(mno), HttpStatus.OK);
    }

    // /reviews/3+Post
    @PostMapping("/{mno}")
    public ResponseEntity<Long> postReview(@RequestBody ReviewDto reviewDto) {
        log.info("리뷰 등록 {}", reviewDto);

        Long reviewNo = reviewService.addReview(reviewDto);
        return new ResponseEntity<Long>(reviewNo, HttpStatus.OK);
    }

    @PreAuthorize("authentication.name == #email")
    @DeleteMapping("/{mno}/{reviewNo}")
    public ResponseEntity<Long> removeReview(@PathVariable("reviewNo") Long reviewNo, String email) {
        log.info("리뷰 삭제 {}");
        reviewService.removeReview(reviewNo);
        return new ResponseEntity<>(reviewNo, HttpStatus.OK);
    }

    // /mno/rno + GET
    @GetMapping("/{mno}/{reviewNo}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable("reviewNo") Long reviewNo) {
        log.info("review 가져오기 {}", reviewNo);

        return new ResponseEntity<>(reviewService.getReview(reviewNo), HttpStatus.OK);
    }

    @PreAuthorize("authentication.name == #reviewDto.email")
    @PutMapping("/{mno}/{reviewNo}")
    public ResponseEntity<Long> putReview(@PathVariable("reviewNo") Long reviewNo, @RequestBody ReviewDto reviewDto) {
        log.info("리뷰 수정 요청 {}, {} ", reviewDto);

        reviewService.updateReview(reviewDto);

        return new ResponseEntity<Long>(reviewNo, HttpStatus.OK);
    }
}