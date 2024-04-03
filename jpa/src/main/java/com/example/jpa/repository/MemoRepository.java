package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Memo;

// <Entity, Entity 에서 기본키(PK) 타입>
public interface MemoRepository extends JpaRepository<Memo, Long> {
    // DAO 역할

}
