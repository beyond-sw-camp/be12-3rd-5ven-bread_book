package com.example.breadbook.domain.book.model;

import lombok.*;

@Getter
public class BookDto {
    @Getter
    public static class BookRequest {
        private String title;
        private String author;
        private String publisher;

        public Book toEntity() {
            return Book.builder()
                    .title(title)
                    .author(author)
                    .publisher(publisher)
                    .build();
        }
    }

    @Getter
    public static class BookResponse {
        private String title;
        private String author;
        private String publisher;

        public static BookResponse from(Book book) {
            BookResponse response = new BookResponse();
            response.title = book.getTitle();
            response.author = book.getAuthor();
            response.publisher = book.getPublisher();
            return response;
        }
    }
}
