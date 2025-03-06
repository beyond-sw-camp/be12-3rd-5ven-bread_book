package com.example.breadbook.domain.review.model;

import com.example.breadbook.domain.product.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

public class ReviewDto {
    @Getter
    @Builder
    public static class ReviewDtoReq{
        @Schema(description = "리뷰 작성자의 idx")
        private Long memberIdx;
        @Schema(description = "리뷰하는 상품의 주문 번호")
        private Long orderIdx;
        @Schema(description = "리뷰의 글자 수")
        private int los;
        @Schema(description = "리뷰하는 주의사항 동의")
        private Boolean agree;
        @Schema(description = "리뷰 내용")
        private String reviewText;
        @Schema(description = "리뷰 점수")
        private int rating;
    }

    @Getter
    @Builder
    public static class ReviewDtoResp{
        @Schema(description = "리뷰 작성자의 이름")
        private String username;
        @Schema(description = "리뷰 상품의 이름")
        private String title;
        @Schema(description = "리뷰 내용")
        private String reviewText;
        @Schema(description = "리뷰 생성 날짜")
        private String createdAt;
        @Schema(description = "리뷰 하는 상품 이미지")
        private String imageUrl;

        public static ReviewDtoResp of(Product product) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            return ReviewDtoResp.builder()
                    .username(product.getMember().getUsername())
                    .title(product.getBook().getTitle())
                    .reviewText(product.getReviews().get(0).getReviewText())
                    .createdAt(product.getReviews().get(0).getCreatedAt().format(formatter))
                    .imageUrl(product.getBook().getTitle())
                    .build();
        }
    }
}
