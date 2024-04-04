package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// 컬럼명 타입 (자릿수, 소수점) null/not null
// id number (19,0) not null - pk 라 nn 기본
// id number (19,0) not null
// id number (10,0) not null
// id number (10,0)

// 기본 타입 : int, long, boolean, char, float, double : null 대임 불가
// 객체 타입(대문자 시작) : Integer, Long, Boolean, ... : null 대입 가능

@Entity
public class Test {
    @Id
    private Long id;

    // @Column(nullable = false)
    @Column(columnDefinition = "number(8)")
    private long id2;
    private int id3;
    private Integer id4;
}
