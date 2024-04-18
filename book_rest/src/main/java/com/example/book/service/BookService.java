package com.example.book.service;

import java.util.List;

import com.example.book.dto.BookDto;
import com.example.book.dto.PageRequestDto;
import com.example.book.dto.PageResultDto;
import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;

public interface BookService {
    // 페이지 나누기 전
    // List<BookDto> getList();

    // 페이지 나누기 후
    PageResultDto<BookDto, Book> getList(PageRequestDto requestDto);

    Long bookCreate(BookDto dto);

    List<String> categoryNameList();

    BookDto getRow(Long id);

    Long bookUpdate(BookDto updateDto);

    void bookDelete(Long id);

    public default BookDto entityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .writer(book.getWriter())
                .categoryName(book.getCategory().getName())
                .publisherName(book.getPublisher().getName())
                .price(book.getPrice())
                .salePrice(book.getSalePrice())
                .createdDate(book.getCreatedDate())
                .lastModifiedDate(book.getLastModifiedDate())
                .build();
    }

    public default Book dtoToEntity(BookDto dto) {
        // Category 와 Publisher 이름이 입력되어 있음

        return Book.builder()
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .price(dto.getPrice())
                .salePrice(dto.getSalePrice())
                .build();
    }

}
