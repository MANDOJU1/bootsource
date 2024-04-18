package com.example.book.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookDto {
    private Long id;

    @NotBlank(message = "제목을 입력해 주세요")
    private String title;

    @NotBlank(message = "작가를 입력해 주세요")
    private String writer;

    @NotNull(message = "가격을 입력해 주세요")
    private Integer price;

    @NotNull(message = "할인가격을 입력해 주세요")
    private Integer salePrice;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    // 관계

    @NotBlank(message = "분류를 입력해 주세요")
    private String categoryName;

    @NotBlank(message = "출판사를 입력해 주세요")
    private String publisherName;
}
