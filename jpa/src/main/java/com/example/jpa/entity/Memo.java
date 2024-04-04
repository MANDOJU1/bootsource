package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*  jpa가 해주는 작업
 *  1) 테이블을 직접 작성하지 않아도 된다
 */

// @Entity : 데이터베이스에서 데이터로 관리하는 대상
// DB랑 연결되어 있는 객체
@Entity
// @Table : 다른 테이블 이름으로 만들고자 할 때
@Table(name = "memotbl")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Memo {
    // @GeneratedValue(전략 = )
    // AUTO : create sequence memotbl_seq start with 1 increment by 50
    // IDENTITY (기본키 생성은 데이터베이스에 위임) : mno number(19,0) generated as identity
    // SEQUENCE : create sequence memotbl_seq start with 1 increment by 50
    @SequenceGenerator(name = "memo_seq_gen", sequenceName = "memo_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memo_seq_gen")
    @Id // = primary key
    private Long mno;

    // 변수명 짓는 방법
    // 자바 변수명은 _ 사용 x 뒤 단어는 대문자 사용(카멜케이스) → memo_text (데이터베이스 컬럼에서는 _ 사용) (스네이크케이스)

    // @Column(nullable = false) : notnull (안 주면 default - true) / length : 텍스트 길이
    @Column(nullable = false, length = 300)
    private String memoText;

}
