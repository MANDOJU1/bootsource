package com.example.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "writer")
@Builder
@Getter
@Setter
@Entity
public class Board extends BaseEntity {

    @SequenceGenerator(name = "board_seq_gen", sequenceName = "board_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_gen")
    @Id
    private Long bno;

    private String title;

    private String content;

    // @ManyToOne - fetch 방식이 즉시 로딩(FetchType.EAGER) - left join 방식으로 데이터 처리
    // - 조회 시 즉시 따라옴
    // 그래서 fetch 타입 변경
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

}
