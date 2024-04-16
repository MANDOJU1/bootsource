package com.example.guestbook.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class GuestBookDto {
    // Dto : Entity + BaseEntity 모양

    private Long gno;

    @NotBlank(message = "작성자를 입력하세요")
    private String writer;

    @NotBlank(message = "제목을 입력하세요")
    private String title;

    @NotBlank(message = "내용을 입력하세요")
    private String content;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}