package com.example.breadbook.domain.product.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "책 분류 정보")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "책 분류의 고유값", example ="38")
    private Long idx;  // 카테고리 고유 ID

    @Schema(description = "분류명", example = "한국소설")
    @Column(nullable = false, length = 100)
    private String name;  // 카테고리 이름

    @Schema(description = "상위 분류의 고유값")
    @ManyToOne
    @JoinColumn(name = "parent_idx", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Category parent;  // 부모 카테고리

}

