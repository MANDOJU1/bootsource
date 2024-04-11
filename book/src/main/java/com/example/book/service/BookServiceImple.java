package com.example.book.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookDto;
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

    @Override
    public List<BookDto> getList() {
        List<Book> books = bookRepository.findAll(Sort.by("id").descending());

        // List<BookDto> bookDtos = new ArrayList<>();
        // books.forEach(book -> bookDtos.add(entityToDto(book)));

        // 위에 두 줄을 간단하게 만들기
        List<BookDto> bookDtos = books.stream().map(book -> entityToDto(book)).collect(Collectors.toList());

        return bookDtos;
    }

    @Override
    public Long bookCreate(BookDto dto) {

        Category category = categoryRepository.findByName(dto.getCategoryName()).get();
        Publisher publisher = publisherRepository.findByName(dto.getPublisherName()).get();

        Book book = dtoToEntity(dto);
        book.setCategory(category);
        book.setPublisher(publisher);

        Book newbBook = bookRepository.save(book);
        return newbBook.getId();

    }

    @Override
    public List<String> categoryNameList() {
        List<Category> list = categoryRepository.findAll();
        List<String> cateList = list.stream().map(entity -> entity.getName()).collect(Collectors.toList());

        return cateList;
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