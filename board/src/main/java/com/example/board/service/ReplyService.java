package com.example.board.service;

import java.util.List;

import com.example.board.dto.ReplyDto;
import com.example.board.entity.Board;
import com.example.board.entity.Reply;

public interface ReplyService {

    List<ReplyDto> getReplies(Long bno);

    Long create(ReplyDto dto);

    void remove(Long rno);

    ReplyDto getReply(Long rno);

    Long update(ReplyDto dto);

    // entity → dto
    public default ReplyDto entityToDto(Reply reply) {
        return ReplyDto.builder()
                .rno(reply.getRno())
                .bno(reply.getBoard().getBno())
                .replyer(reply.getReplyer())
                .text(reply.getText())
                .createdDate(reply.getCreatedDate())
                .lastModifiedDate(reply.getLastModifiedDate())
                .build();
    }

    // dto → entity
    public default Reply dtoToEntity(ReplyDto dto) {
        // 부모 가져오기
        Board board = Board.builder().bno(dto.getBno()).build();

        return Reply.builder()
                .rno(dto.getRno())
                .board(board)
                .replyer(dto.getReplyer())
                .text(dto.getText())
                .build();
    }

}
