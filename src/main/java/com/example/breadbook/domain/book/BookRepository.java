package com.example.breadbook.domain.book;

import com.example.breadbook.domain.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE (:idx IS NULL OR b.idx = :idx) " +
            "AND (:title IS NULL OR b.title LIKE %:title%) " +
            "AND (:author IS NULL OR b.author LIKE %:author%) " +
            "AND (:publisher IS NULL OR b.publisher LIKE %:publisher%)")
    List<Book> findBooks(@Param("idx") Long idx, @Param("title") String title,
                         @Param("author") String author, @Param("publisher") String publisher);
}
