package com.example.breadbook.domain.report.model;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Long member_idx;
    private Long product_idx;
    private String report_reason;
    private Date create_at;

    @OneToOne
    @JoinColumn(name = "member_idx")
    private Member member;

    @OneToOne
    @JoinColumn(name = "product_idx")
    private Product product;
}
