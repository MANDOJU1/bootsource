package com.example.board.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.board.entity.Board;
import com.example.board.entity.Member;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder().email("user" + i + "@gmail.com").build();

            Board board = Board.builder()
                    .title("title..." + i)
                    .content("content..." + i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        });
    }

    @Transactional
    @Test
    public void readBoard() {
        Board board = boardRepository.findById(2L).get();
        System.out.println(board);
        // Board(bno=2, title=title...2, content=content...2,
        // writer=Member(email=user2@gmail.com, password=1111, name=USER2))
        // @ManyToOne - fetch 방식이 즉시 로딩(FetchType.EAGER)
        // 그래서 board 조회 시 member 가 따라옴
        System.out.println(board.getWriter());
    }

    @Test
    public void testList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        // where b1_0.bno>? and (b1_0.title like ? escape '!' or b1_0.content like ?
        // escape '!' or w1_0.email like ? escape '!'
        Page<Object[]> list = boardRepository.list("tcw", "Title", pageable);
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
            // Board board = (Board) objects[0];
            // Member member = (Member) objects[1];
            // Long replyCnt = (Long) objects[2];
            // System.out.println(board + " " + member + " " + replyCnt);
        }
    }

    @Test
    public void testGetRow() {
        Object[] row = boardRepository.getRow(3L);
        System.out.println(Arrays.toString(row));
    }

    @Transactional
    @Test
    public void testRemove() {
        // 두 개를 하나의 작업으로 묶어야함 (둘 중 하나라도 실패하면 원상복구) - @Transactional
        // 하나의 트렌젝션으로 실행

        // 자식(댓글) 삭제
        replyRepository.deleteByBno(100L);

        // 부모(원본글) 삭제
        boardRepository.deleteById(100L);
    }
}
