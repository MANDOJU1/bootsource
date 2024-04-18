package com.example.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.book.dto.BookDto;
import com.example.book.dto.PageRequestDto;
import com.example.book.dto.PageResultDto;
import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;
import com.example.book.repository.BookRepository;
import com.example.book.repository.CategoryRepository;
import com.example.book.repository.PublisherRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookServiceImple implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    // 페이지 나누기 전
    // @Override
    // public List<BookDto> getList() {
    // // 정렬
    // List<Book> books = bookRepository.findAll(Sort.by("id").descending());

    // // List<BookDto> bookDtos = new ArrayList<>();
    // // books.forEach(book -> bookDtos.add(entityToDto(book)));
    // List<BookDto> bookDtos = books.stream().map(book ->
    // entityToDto(book)).collect(Collectors.toList());

    // return bookDtos;
    // }

    // 페이지 나누기 후
    @Override
    public PageResultDto<BookDto, Book> getList(PageRequestDto requestDto) {
        Pageable pageable = requestDto.getPageable(Sort.by("id").descending());

        Page<Book> result = bookRepository
                .findAll(bookRepository.makePredicate(requestDto.getType(), requestDto.getKeyword()), pageable);
        Function<Book, BookDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<>(result, fn);
    }

    @Override
    public Long bookCreate(BookDto dto) {
        Category category = categoryRepository.findByName(dto.getCategoryName()).get();
        Publisher publisher = publisherRepository.findByName(dto.getPublisherName()).get();

        Book book = dtoToEntity(dto);
        book.setCategory(category);
        book.setPublisher(publisher);

        Book newBook = bookRepository.save(book);
        return newBook.getId();
    }

    @Override
    public List<String> categoryNameList() {
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(entity -> entity.getName()).collect(Collectors.toList());
    }

    @Override
    public BookDto getRow(Long id) {
        Book entity = bookRepository.findById(id).get();
        return entityToDto(entity);
    }

    @Override
    public Long bookUpdate(BookDto updateDto) {
        // 수정할 대상 찾기
        Book entity = bookRepository.findById(updateDto.getId()).get();
        // 수정할 값 저장
        entity.setPrice(updateDto.getPrice());
        entity.setSalePrice(updateDto.getSalePrice());
        Book updateBook = bookRepository.save(entity);

        return updateBook.getId();
    }

    @Override
    public void bookDelete(Long id) {
        Book entity = bookRepository.findById(id).get();
        bookRepository.delete(entity);
    }

}
