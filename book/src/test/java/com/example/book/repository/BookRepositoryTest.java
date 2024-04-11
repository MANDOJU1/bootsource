package com.example.book.repository;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    public void testCategoryCreate() {

        Category category = Category.builder().name("컴퓨터").build();
        categoryRepository.save(category);

        category = Category.builder().name("경제/경영").build();
        categoryRepository.save(category);

        category = Category.builder().name("인문").build();
        categoryRepository.save(category);

        category = Category.builder().name("소설").build();
        categoryRepository.save(category);

        category = Category.builder().name("자기계발").build();
        categoryRepository.save(category);
    }

    @Test
    public void testPublisherCreate() {

        Publisher publisher = Publisher.builder().name("로드북").build();
        publisherRepository.save(publisher);

        publisher = Publisher.builder().name("다산").build();
        publisherRepository.save(publisher);

        publisher = Publisher.builder().name("웅진지식하우스").build();
        publisherRepository.save(publisher);

        publisher = Publisher.builder().name("비룡소").build();
        publisherRepository.save(publisher);

        publisher = Publisher.builder().name("을유문화사").build();
        publisherRepository.save(publisher);
    }

    @Test
    public void testBookCreate() {

        LongStream.rangeClosed(1, 20).forEach(i -> {
            Book book = Book.builder()
                    .price(25000)
                    .salePrice(22500)
                    .title("스프링 부트 프레임워크 " + i)
                    .writer("홍길동")
                    .category(Category.builder().id((i % 5) + 1).build())
                    .publisher(Publisher.builder().id((i % 5) + 1).build())
                    .build();

            bookRepository.save(book);
        });

    }
    // (i % 5) + 1
}