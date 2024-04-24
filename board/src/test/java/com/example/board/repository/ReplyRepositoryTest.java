package com.example.board.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;

@SpringBootTest
public class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            long bno = (long) (Math.random() * 100) + 1;

            Board board = Board.builder().bno(bno).build();
            Member member = Member.builder().email("user1@naver.com").build();

            Reply reply = Reply.builder()
                    .text("Reply..." + i)
                    .replyer(member)
                    .board(board)
                    .build();
            replyRepository.save(reply);
        });
    }

    @Transactional
    @Test
    public void getRow() {
        Reply reply = replyRepository.findById(2L).get();
        System.out.println(reply); // Reply(rno=2, text=Reply...2, replyer=guest2)

        // FetchType.LAZY 이기 때문에 reply 부모 게시물은 안 가지고 옴
        // 해결 방안 => @Transactional 붙이기
        System.out.println(reply.getBoard()); // Board(bno=3, title=title...3, content=content...3)
    }

    @Test
    public void getReplies() {
        Board board = Board.builder().bno(34L).build();
        List<Reply> replies = replyRepository.getRepliesByBoardOrderByRno(board);

        System.out.println(replies.toString());
        // => 결과 값 [Reply(rno=15, text=Reply...15, replyer=guest15), Reply(rno=51,
        // text=Reply...51, replyer=guest51)]
    }
}
