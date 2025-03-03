package com.example.breadbook.domain.wish.model;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "product_idx", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "member_idx", nullable = false)
    private Member member;

    @Setter
    private boolean canceled = false;
}
