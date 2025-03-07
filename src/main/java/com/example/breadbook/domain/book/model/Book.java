package com.example.breadbook.domain.book.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Schema(example = "55")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;  // 책 고유 ID

    @Schema(example="9791191114065")
    @Column(unique = true, nullable = false, length = 13)
    private Long isbn;  // ISBN 번호

    @Schema(example = "빛의 제국 (김영하 장편소설)")
    @Column(nullable = false, length = 255)
    private String title;  // 책 제목

    @Schema(example = "김영하")
    @Column(nullable = false, length = 255)
    private String author;  // 저자

    @Schema(example = "복복서가")
    @Column(length = 255)
    private String publisher;  // 출판사

    @Schema(example = "2022-05-23")
    private LocalDate publicationDate;  // 출판일

    @Schema(example="https://shopping-phinf.pstatic.net/main_3248332/32483329961.20230801120650.jpg")
    private String bookImageUrl;
}
