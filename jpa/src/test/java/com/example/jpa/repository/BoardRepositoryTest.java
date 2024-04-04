package com.example.jpa.repository;

import static org.mockito.ArgumentMatchers.isNull;

import java.util.Optional;
import java.util.stream.LongStream;

import javax.swing.text.html.Option;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertTest() {
        // LongStream.range(1, 100); : 1~99
        // LongStream.rangeClosed(1, 100); : 1~100
        LongStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer("user" + (i % 10))
                    .build();

            boardRepository.save(board);
        });
    }

    @Test
    public void readTest() {
        // l 소문자 가능 (구분 위해 L 사용)
        System.out.println(boardRepository.findById(5L));
    }

    @Test
    public void getListTest() {
        boardRepository.findAll().forEach(board -> System.out.println(board));
    }

    @Test
    public void updateTest() {
        Optional<Board> result = boardRepository.findById(26L);

        // result.get()
        result.ifPresent(board -> {
            board.setTitle("Update Title...");
            board.setContent("Update Content...");
            System.out.println(boardRepository.save(board));
        });
    }

    @Test
    public void deleteTest() {
        // entity 객체 찾기
        Optional<Board> result = boardRepository.findById(28L);

        // 삭제
        boardRepository.delete(result.get());
        System.out.println(boardRepository.findById(28L));
    }
}
