package com.example.breadbook.domain.review.model;

import com.example.breadbook.domain.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

public class ReviewDto {
    @Getter
    @Builder
    public static class ReviewDtoReq{
        private Long memberIdx;
        private Long orderIdx;
        private int los;
        private Boolean agree;
        private String reviewText;
        private int rating;
    }

    @Getter
    @Builder
    public static class ReviewDtoResp{
        private String username;
        private String title;
        private String reviewText;
        private String createdAt;
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
