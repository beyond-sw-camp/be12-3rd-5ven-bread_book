package com.example.breadbook.domain.review;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.review.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMember(Member member);

    Optional<Review> findByProduct(Product product);

    @EntityGraph(attributePaths = {"product", "product.member", "product.book","member"})
    @Query("SELECT r FROM Review r " +
            "LEFT JOIN r.product p " +
            "LEFT JOIN p.member m " +
            "LEFT JOIN p.book b " +
            "WHERE m.idx = :memberIdx")
    Page<Review> findByReviewWithMember(Long memberIdx, Pageable pageable);
}
