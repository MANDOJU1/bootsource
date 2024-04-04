package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.dto.MemoDto;
import com.example.jpa.entity.Memo;

// <Entity, Entity에서 pk로 쓰이는 컬럼 타입> : ~와 연결 되어 있음
public interface MemoRepository extends JpaRepository<Memo, Long> {

    void save(MemoDto dtoToEntity);
    // DAO 역할
}
