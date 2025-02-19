package com.example.breaadbook.domain.review.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewDto {
    @Getter
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class ReviewDtoReq{
        private Long memberIdx;
        private Long productIdx;
        private String reviewText;
        private int rating;
    }
}
