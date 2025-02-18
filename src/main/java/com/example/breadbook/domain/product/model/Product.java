package com.example.breadbook.domain.product.model;


import com.example.breadbook.domain.book.model.Book;
import com.example.breadbook.domain.category.model.Category;
import com.example.breadbook.domain.product.BookCondition;
import com.example.breadbook.domain.product.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;  // 판매 상품 고유 ID

    @Column(nullable = false)
    private Long memberIdx;  // 판매자 ID (회원 테이블 참조)

    @ManyToOne
    @JoinColumn(name = "book_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Book book;  // 판매되는 책 정보

    @ManyToOne
    @JoinColumn(name = "category_idx", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Category category;  // 카테고리 참조

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;  // 가격

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private BookCondition bookCondition;  // 책 상태 (ENUM)

    @Column(length = 100)
    private String tradeMethod;  // 거래 선호 방식

    @Column(length = 255)
    private String paymentLocation;  // 거래 장소 (직거래 시)

    @Lob
    private String description;  // 상품 설명

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();  // 등록일시

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProductStatus productStatus;  // 판매 상태 (ENUM)

    @Column(length = 255)
    private String bookImage;  // 책 이미지 URL
}

