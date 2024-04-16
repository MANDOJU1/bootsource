package com.example.guestbook.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
public class PageResultDto<DTO, EN> {
    // entity 타입의 리스트를 dto 타입 리스트로 변환
    private List<DTO> dtoList;

    // 화면에서 시작 페이지 번호
    // 화면에서 끝 페이지 번호
    private int start, end;

    // 이전 / 다음 이동 링크 여부
    private boolean prev, next;

    // 현재 페이지 번호
    private int page;

    // 총 페이지 번호
    private int totalPage;

    // 목록 사이즈
    private int size;

    // 페이지 번호 목록
    private List<Integer> pageList;

    // Page<EN> result : 스프링에서 페이지 나누기와 관련된 모든 정보를 가지고 있는 객체
    // Function<EN, DTO> fn : entity 타입에서 dto 타입으로 바꿈 (람다식 메서드 사용)
    public PageResultDto(Page<EN> result, Function<EN, DTO> fn) {
        this.dtoList = result.stream().map(fn).collect(Collectors.toList());

        this.totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    public void makePageList(Pageable pageable) {

        // spring 에서 페이지는 0 부터 시작하기 때문에 + 1 를 해줌
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        // int tempend = (int)(Math.ceil(페이지번호 / 10.0)) * 10;
        // Math.ceil(1 / 10.0) = 0.1 * 10
        int tempend = (int) (Math.ceil(page / 10.0)) * 10;

        this.start = tempend - 9;
        this.end = totalPage > tempend ? tempend : totalPage;

        this.prev = start > 1;
        this.next = totalPage > tempend;

        // List<Integer> 타입
        // .collect(Collectors.toList() : List로 보내고 싶으면 쓰기 !
        this.pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    }

}
