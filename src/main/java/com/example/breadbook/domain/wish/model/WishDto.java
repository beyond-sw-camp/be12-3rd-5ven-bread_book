package com.example.breadbook.domain.wish.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class WishDto {
    @Getter @Builder
    @AllArgsConstructor @NoArgsConstructor
    @Schema(description = "찜하기 응답 DTO")
    public static class RegisterResponse {
        @Schema(description = "상품의 고유값", example = "10")
        private Long productIdx;
        @Schema(description = "취소여부", example = "false")
        private Boolean canceled;

        public static RegisterResponse of(Wish wish) {
            return builder().productIdx(wish.getProduct().getIdx()).canceled(wish.isCanceled()).build();
        }
    }
}
