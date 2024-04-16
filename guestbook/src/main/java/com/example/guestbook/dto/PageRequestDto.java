package com.example.guestbook.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDto {

    private int page;
    private int size;

    // 검색 기능
    private String type;
    private String keyword;

    // 만약에 화면단에서 page, size 요청이 들어오지 않는다면 기본값으로 이렇게 설정함
    public PageRequestDto() {
        this.page = 1;
        this.size = 10;
    }

    // 스프링 페이지 나누기 정보 저장 => Pageable
    // 1 page는 0부터 시작이기 때문에 page - 1 해야함
    // 1page => 0, 2page => 1, ...
    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }

}
