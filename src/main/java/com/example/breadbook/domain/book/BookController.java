package com.example.breadbook.domain.book;

import com.example.breadbook.domain.book.model.BookDto;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "도서 정보 관리 기능")
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    // (1) 책 검색 기능
    @Operation(summary = "도서 검색 기능", description = "도서 정보 DB에서 등록된 책을 검색하는 기능이다.")
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<BookDto.BookResponse>>> searchBooks(
            @RequestParam(required = false) Long idx,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String publisher) {
        List<BookDto.BookResponse> books = bookService.searchBooks(idx, title, author, publisher);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.REQUEST_SUCCESS, books));
    }

    // (2) 책 등록 기능
    @Operation(summary = "도서 정보 등록 기능", description = "도서 정보 DB에 없는 책을 등록하는 기능이다.")
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<BookDto.BookResponse>> registerBook(@RequestBody BookDto.BookRequest dto) {
        BookDto.BookResponse book = bookService.registerBook(dto);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.REQUEST_SUCCESS, book));
    }
}
