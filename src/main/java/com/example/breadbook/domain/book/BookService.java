package com.example.breadbook.domain.book;

import com.example.breadbook.domain.book.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    public List<BookDto.BookResponse> searchBooks(Long idx, String title, String author, String publisher) {
        List<Book> books = bookRepository.findBooks(idx, title, author, publisher);
        return books.stream().map(BookDto.BookResponse::from).toList();
    }

    public BookDto.BookResponse registerBook(BookDto.BookRequest dto) {
        Book book = bookRepository.save(dto.toEntity());
        return BookDto.BookResponse.from(book);
    }
}
