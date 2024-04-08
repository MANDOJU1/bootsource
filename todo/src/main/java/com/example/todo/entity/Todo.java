package com.example.todo.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

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

@DynamicInsert // default 동작시키기 위해
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "todotbl")
@Entity
public class Todo extends BaseEntity {

    @SequenceGenerator(name = "todo_seq_gen", sequenceName = "todo_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_seq_gen")

    @Column(name = "todo_id")
    @Id
    private Long id;

    // @Column(nullable = false)
    @ColumnDefault("0") // sql 구문에서 defalut 값을 설정하는 것과 같음
    private boolean completed;

    // @Column(nullable = false)
    @ColumnDefault("0")
    private boolean important;

    @Column(nullable = false)
    private String title;

    // default 값을 삽입 : 아무것도 입력이 되지 않으면 defalut 값으로 입력
    // JPA 에서는 defalut 값으로 자동 삽입하려면 @DynamicInsert 필요함
    // @DynamicInsert : 데이터가 존재하는 필드만으로 insert sql 문 생성 ==> not null 이 아닌 필드만 해줌

}
