package com.example.breadbook.domain.book;

import com.example.breadbook.domain.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
