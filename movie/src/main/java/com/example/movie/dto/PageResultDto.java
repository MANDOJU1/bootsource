package com.example.movie.dto;

import java.util.ArrayList;
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

    // 이전/다음 이동 링크 여부
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
    // Function<EN, DTO> fn : entity => dto 메소드 사용
    public PageResultDto(Page<EN> result, Function<EN, DTO> fn) {
        this.dtoList = result.stream().map(fn).collect(Collectors.toList());

        this.totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    public void makePageList(Pageable pageable) {

        // spring 페이지는 0부터 시작
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        // Math.ceil(1 / 10.0) = 0.1 * 10
        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;
        this.start = tempEnd - 9;
        this.end = totalPage > tempEnd ? tempEnd : totalPage;

        this.prev = start > 1;
        this.next = totalPage > tempEnd;

        this.pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}
