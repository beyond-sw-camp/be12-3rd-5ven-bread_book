package com.example.breadbook.domain.book.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
public class BookDto {
    @Schema(description = "도서 등록 DTO")
    @Getter
    public static class BookRequest {
        @Schema(description = "도서의 고유한 ISBN 값", example = "9791191114065")
        private Long isbn;
        @Schema(description = "도서의 제목", example = "빛의 제국 (김영하 장편소설)")
        private String title;
        @Schema(description = "도서의 저자", example = "김영하")
        private String author;
        @Schema(description = "도서 출판사", example = "복복서가")
        private String publisher;
        @Schema(description = "출판일자", example = "2022-05-23")
        private LocalDate publicationDate;
        @Schema(description = "이미지", example = "https://shopping-phinf.pstatic.net/main_3248332/32483329961.20230801120650.jpg")
        private String bookImageUrl;

        public Book toEntity() {
            return Book.builder()
                    .isbn(isbn)
                    .title(title)
                    .author(author)
                    .publisher(publisher)
                    .publicationDate(publicationDate)
                    .bookImageUrl(bookImageUrl)
                    .build();
        }
    }

    @Getter
    public static class BookResponse {
        @Schema(description = "도서의 고유값", example = "55")
        private Long idx;
        @Schema(description = "도서의 제목", example = "빛의 제국 (김영하 장편소설)")
        private String title;
        @Schema(description = "도서의 저자", example = "김영하")
        private String author;
        @Schema(description = "도서 출판사", example = "복복서가")
        private String publisher;
        @Schema(description = "출판일자", example = "2022-05-23")
        private LocalDate publicationDate;

        public static BookResponse from(Book book) {
            BookResponse response = new BookResponse();
            response.idx = book.getIdx();
            response.title = book.getTitle();
            response.author = book.getAuthor();
            response.publisher = book.getPublisher();
            response.publicationDate = book.getPublicationDate();
            return response;
        }
    }
}
