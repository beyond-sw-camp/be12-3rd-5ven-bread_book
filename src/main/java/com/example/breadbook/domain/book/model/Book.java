package com.example.breadbook.domain.book.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;  // 책 고유 ID

    @Column(unique = true, nullable = false, length = 13)
    private Long isbn;  // ISBN 번호

    @Column(nullable = false, length = 255)
    private String title;  // 책 제목

    @Column(nullable = false, length = 255)
    private String author;  // 저자

    @Column(length = 255)
    private String publisher;  // 출판사

    private LocalDate publicationDate;  // 출판일
}
