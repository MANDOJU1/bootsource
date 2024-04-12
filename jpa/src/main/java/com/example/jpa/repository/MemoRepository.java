package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.dto.MemoDto;
import com.example.jpa.entity.Memo;

// <Entity, Entity에서 pk로 쓰이는 컬럼 타입> : ~와 연결 되어 있음
public interface MemoRepository extends JpaRepository<Memo, Long> {
    // void save(MemoDto dtoToEntity);
    // DAO 역할

    // mno 가 5보다 작은 메모 조회
    List<Memo> findByMnoLessThan(Long mno);

    // mno 가 10 보다 작은 메모 조회(mno 내림차순)
    List<Memo> findByMnoLessThanOrderByMnoDesc(Long mno);

    // mno >= 50 and mno <= 70 메모 조회
    List<Memo> findByMnoBetween(Long start, Long end);
}
