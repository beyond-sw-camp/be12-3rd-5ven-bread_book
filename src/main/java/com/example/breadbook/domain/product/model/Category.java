package com.example.breadbook.domain.product.model;

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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;  // 카테고리 고유 ID

    @Column(nullable = false, length = 100)
    private String name;  // 카테고리 이름

    @ManyToOne
    @JoinColumn(name = "parent_idx", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Category parent;  // 부모 카테고리

}

