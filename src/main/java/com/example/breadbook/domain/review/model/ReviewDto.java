package com.example.breadbook.domain.review.model;

import com.example.breadbook.domain.order.model.Order;
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
        @Schema(description = "리뷰 작성자의 idx", example = "1")
        private Long memberIdx;
        @Schema(description = "리뷰하는 상품의 주문 번호", example = "1")
        private Long orderIdx;
        @Schema(description = "리뷰의 글자 수(20자 이상)", example = "20")
        private int los;
        @Schema(description = "리뷰하는 주의사항 동의", example = "true")
        private Boolean agree;
        @Schema(description = "리뷰 내용",
                example = "상품 상태가 좋고, 거래도 깔끔하게 했습니다. 너무 너무 좋아요. 좋은 상품 감사합니다."
        )
        private String reviewText;
        @Schema(description = "리뷰 점수", example = "4")
        private int rating;
    }

    @Getter
    @Builder @NoArgsConstructor @AllArgsConstructor
    public static class ReviewDtoResp{
        @Schema(description = "리뷰 작성자의 이름")
        private String username;
        @Schema(description = "리뷰 상품의 이름")
        private String title;
        @Schema(description = "리뷰 내용")
        private String reviewText;
        @Schema(description = "리뷰 작성 날짜")
        private String createdAt;
        @Schema(description = "책 이미지")
        private String imageUrl;
        @Schema(description = "리뷰 넘버")
        private Long reviewIdx;
        @Schema(description = "판매자 넘버")
        private Long memberIdx;

        public static ReviewDtoResp of(Review review) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if(review == null){

                return new ReviewDtoResp();
            }
            return ReviewDtoResp.builder()
                    .username(review.getMember().getUsername())
                    .title(review.getProduct().getBook().getTitle())
                    .reviewText(review.getReviewText())
                    .createdAt(review.getCreatedAt().format(formatter))
                    .imageUrl(review.getProduct().getBook().getBookImageUrl())
                    .reviewIdx(review.getIdx())
                    .memberIdx(review.getProduct().getMember().getIdx())
                    .build();
        }
    }

    @Getter
    public static class ReviewListReq{
        private int page;
        private Long memberIdx;
    }
}
