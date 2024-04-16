package com.example.guestbook.service;

import java.util.List;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.dto.PageRequestDto;
import com.example.guestbook.dto.PageResultDto;
import com.example.guestbook.entity.GuestBook;

public interface GuestBookService {

    // 페이지 나누기 전
    // List<GuestBookDto> getList();

    // 페이지 나눈 후
    PageResultDto<GuestBookDto, GuestBook> getList(PageRequestDto requestDto);

    GuestBookDto getRow(Long gno);

    Long bookUpdate(GuestBookDto updateDto);

    void bookDelete(Long gno);

    Long insert(GuestBookDto dto);

    // entity → dto
    public default GuestBookDto entityToDto(GuestBook guestBook) {
        return GuestBookDto.builder()
                .gno(guestBook.getGno())
                .title(guestBook.getTitle())
                .writer(guestBook.getWriter())
                .content(guestBook.getContent())
                .createdDate(guestBook.getCreatedDate())
                .lastModifiedDate(guestBook.getLastModifiedDate())
                .build();
    }

    // dto → entity
    public default GuestBook dtoToEntity(GuestBookDto dto) {
        return GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .content(dto.getContent())
                .build();
    }
}