package com.example.board.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {
    // @Query(select m, t from Member m join m, team t = ?1)
    // 전체조회 시 board, member, reply 정보를 다 조회
    Page<Object[]> list(String type, String keyword, Pageable pageable);

    // 상세조회
    Object[] getRow(Long bno);
}
