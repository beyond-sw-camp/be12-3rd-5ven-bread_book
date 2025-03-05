package com.example.breadbook.domain.review.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
