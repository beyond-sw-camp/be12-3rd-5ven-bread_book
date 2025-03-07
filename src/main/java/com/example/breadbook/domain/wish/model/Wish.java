package com.example.breadbook.domain.wish.model;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@BatchSize(size = 100)
public class Wish {
    @Schema(description = "찜 고유값", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Schema(description = "상품의 고유값", example = "10")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_idx", nullable = false)
    private Product product;

    @Schema(description = "찜한 사용자")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", nullable = false)
    private Member member;

    @Schema(description = "취소여부", example = "false")
    @Setter
    private boolean canceled = false;
}
