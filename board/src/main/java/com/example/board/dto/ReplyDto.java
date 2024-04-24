package com.example.board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReplyDto {
    private Long rno;

    private String text;

    private String writerEmail; // 작성자 아이디(email)
    private String writerName; // 작성자 이름

    private Long bno; // 부모 글 번호

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
