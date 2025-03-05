package com.example.breadbook.domain.order.model;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.review.model.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "member_idx", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_idx", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt ;

    @OneToOne(mappedBy = "order", optional = true)
    private Review review;
}