package com.example.breaadbook.domain.review.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "member_idx", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_idx", nullable = false)
    private Product product;

    @Column(name = "review_text")
    private String reviewText;

    @Column(nullable = false)
    private int rating;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

}