package com.example.breadbook.domain.book;

import com.example.breadbook.domain.book.model.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    // (1) 책 검색 기능
    @GetMapping("/search")
    public ResponseEntity<List<BookDto.BookResponse>> searchBooks(
            @RequestParam(required = false) Long idx,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String publisher) {
        List<BookDto.BookResponse> books = bookService.searchBooks(idx, title, author, publisher);
        return ResponseEntity.ok(books);
    }

    // (2) 책 등록 기능
    @PostMapping("/register")
    public ResponseEntity<BookDto.BookResponse> registerBook(@RequestBody BookDto.BookRequest dto) {
        BookDto.BookResponse book = bookService.registerBook(dto);
        return ResponseEntity.ok(book);
    }
}
