package com.example.guestbook.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.guestbook.entity.GuestBook;

@SpringBootTest
public class GuestBookRepositoryTest {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Test
    public void testInsert() {
        // 300개 테스트 데이터 삽입
        IntStream.rangeClosed(1, 300).forEach(i -> {
            GuestBook guestBook = GuestBook.builder()
                    .writer("user" + (i % 10))
                    .title("Guest Title..." + i)
                    .content("Guest Title...")
                    .build();

            guestBookRepository.save(guestBook);
        });
    }

    @Test
    public void testList() {
        // 전체 리스트
        guestBookRepository.findAll().forEach(guestbook -> System.out.println(guestbook));
    }

    @Test
    public void testRow() {
        // 특정 Row 조회
        System.out.println(guestBookRepository.findById(20L));
    }

    @Test
    public void testUpdate() {
        // 특정 Row 수정 (title, content)
        GuestBook entity = guestBookRepository.findById(1L).get();
        entity.setTitle("수정 Title...");
        entity.setContent("수정 Content...");
        System.out.println(guestBookRepository.save(entity));
    }

    @Test
    public void testDelete() {
        // 특정 Row 삭제
        // 1번 방법
        GuestBook guestBook = guestBookRepository.findById(10L).get();
        guestBookRepository.delete(guestBook);

        // 2번 방법
        // guestBookRepository.deleteById(10L);

    }
}
