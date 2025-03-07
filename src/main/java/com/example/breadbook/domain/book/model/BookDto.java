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
        private Long idx;
        private String title;
        private String author;
        private String publisher;
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
