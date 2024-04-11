package com.example.book.service;

import java.util.List;

import com.example.book.dto.BookDto;
import com.example.book.entity.Book;

public interface BookService {
    List<BookDto> getList();

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
                .id(dto.getId())
                .title(dto.getTitle())
                .writer(dto.getTitle())
                .price(dto.getPrice())
                .salePrice(dto.getSalePrice())
                .build();
    }

}
