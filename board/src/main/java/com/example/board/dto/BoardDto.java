package com.example.board.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
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
public class BoardDto {

    private Long bno;

    @NotBlank(message = "제목 입력")
    private String title;

    @NotBlank(message = "내용 입력")
    private String content;

    @NotBlank(message = "작성자 입력")
    private String writerEmail; // 작성자 아이디(email)

    private String writerName; // 작성자 이름
    private Long replyCount; // 해당 게시물의 댓글 수

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
