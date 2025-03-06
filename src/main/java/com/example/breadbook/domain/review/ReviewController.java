package com.example.breadbook.domain.review;

import com.example.breadbook.domain.review.model.Review;
import com.example.breadbook.domain.review.model.ReviewDto;
import com.example.breadbook.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping("/regist")
    @Operation(summary = "리뷰 작성", description = "리뷰를 작성하는 기능입니다.")
    public ResponseEntity<BaseResponse<Review>> registerReview(@RequestBody ReviewDto.ReviewDtoReq dto) {
        return ResponseEntity.ok(reviewService.regist(dto));
    }

    @GetMapping("/find/{idx}")
    @Operation(summary = "리뷰 목록", description = "자신의 상품의 리뷰를 확인하는 기능입니다.")
    public ResponseEntity<BaseResponse<List<Review>>> reviewFind(@PathVariable Long memberIdx) {
        return ResponseEntity.ok(reviewService.findReview(memberIdx));
    }
}
