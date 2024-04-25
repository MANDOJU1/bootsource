package com.example.movie.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReviewDto {

    private Long reviewNo;
    private int grade;
    private String text;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    // 멤버 관계
    private Long mid;
    private String email;
    private String nickname;

    // 영화 관계
    private Long mno;
}
