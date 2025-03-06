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
    @Builder
    public static class ReviewDtoResp{
        @Schema(description = "리뷰 작성자의 이름", example = "홍길동")
        private String username;
        @Schema(description = "리뷰 상품의 이름", example = "어린왕자")
        private String title;
        @Schema(description = "리뷰 내용", example = "상품 상태가 좋고, 거래도 깔끔하게 했습니다. 너무 너무 좋아요. 좋은 상품 감사합니다.")
        private String reviewText;
        @Schema(description = "2024-11-20")
        private String createdAt;
        @Schema(description = "https://www.madtimes.org/news/photo/202107/8707_19214_1235.jpg")
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
