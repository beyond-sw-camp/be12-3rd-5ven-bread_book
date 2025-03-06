package com.example.breadbook.domain.book.model;

import lombok.*;

import java.time.LocalDate;

@Getter
public class BookDto {
    @Getter
    public static class BookRequest {
        private Long isbn;
        private String title;
        private String author;
        private String publisher;
        private LocalDate publicationDate;

        public Book toEntity() {
            return Book.builder()
                    .isbn(isbn)
                    .title(title)
                    .author(author)
                    .publisher(publisher)
                    .publicationDate(publicationDate)
                    .build();
        }
    }

    @Getter
    public static class BookResponse {
        private String title;
        private String author;
        private String publisher;
        private LocalDate publicationDate;

        public static BookResponse from(Book book) {
            BookResponse response = new BookResponse();
            response.title = book.getTitle();
            response.author = book.getAuthor();
            response.publisher = book.getPublisher();
            response.publicationDate = book.getPublicationDate();
            return response;
        }
    }
}
