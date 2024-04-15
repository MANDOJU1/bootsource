package com.example.guestbook.service;

import java.util.List;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.entity.GuestBook;

public interface GuestBookService {
    public List<GuestBookDto> getList();

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
    public default GuestBookDto dtoToEntity(GuestBookDto dto) {
        return GuestBookDto.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .content(dto.getContent())
                .build();
    }
}