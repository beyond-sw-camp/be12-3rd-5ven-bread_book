package com.example.breaadbook.domain.review;

import com.example.breaadbook.domain.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
