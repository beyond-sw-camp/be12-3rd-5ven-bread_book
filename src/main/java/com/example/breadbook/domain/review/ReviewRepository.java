package com.example.breadbook.domain.review;

import com.example.breadbook.domain.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {



    List<Review> findByMember(Long memberIdx);
}
