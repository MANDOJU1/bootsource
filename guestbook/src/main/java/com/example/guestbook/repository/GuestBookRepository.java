package com.example.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.guestbook.entity.GuestBook;

// QuerydslPredicateExecutor<GuestBook>
// 동적 쿼리 필요 - 검색
public interface GuestBookRepository extends JpaRepository<GuestBook, Long>, QuerydslPredicateExecutor<GuestBook> {

}
