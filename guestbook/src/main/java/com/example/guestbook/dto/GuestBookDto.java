package com.example.guestbook.dto;

import java.time.LocalDateTime;

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

    private String writer;

    private String title;

    private String content;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}