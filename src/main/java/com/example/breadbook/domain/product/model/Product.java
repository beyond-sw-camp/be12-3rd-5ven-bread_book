package com.example.breadbook.domain.product.model;

import com.example.breadbook.domain.book.model.Book;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.BookCondition;
import com.example.breadbook.domain.product.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;  // 판매 상품 고유 ID

    @ManyToOne
    @JoinColumn(name="member_idx")
    private Member member;  // 판매자 ID (회원 테이블 참조)

    @ManyToOne
    @JoinColumn(name = "book_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Book book;  // 판매되는 책 정보

    @ManyToOne
    @JoinColumn(name = "category_idx", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Category category;  // 카테고리 참조

    private Long price;  // 가격

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private BookCondition bookCondition;  // 책 상태 (ENUM)


    @Column(nullable = false, length = 255)
    private String tradeMethod;  // 거래 선호 방식
    @Column(nullable = true, length = 255)
    private String tradeLocation;  // 거래 장소 (직거래 시)
    @Column(nullable = false, length = 1000)
    private String description;  // 상품 설명
    private LocalDateTime createdAt = LocalDateTime.now();  // 등록일시

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;  // 판매 상태 (ENUM)

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImageList = new ArrayList<>();
}