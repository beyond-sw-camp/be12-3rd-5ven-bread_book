package com.example.breadbook.domain.review;

import com.example.breadbook.domain.review.model.Review;
import com.example.breadbook.domain.review.model.ReviewDto;
import com.example.breadbook.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping("/regist")
    public ResponseEntity<BaseResponse<Review>> registerReview(@RequestBody ReviewDto.ReviewDtoReq dto) {
        return ResponseEntity.ok(reviewService.regist(dto));
    }

    @GetMapping("/find/{idx}")
    public ResponseEntity<BaseResponse<Review>> reviewFind(@PathVariable Long productIdx) {
        return ResponseEntity.ok(reviewService.findReview(productIdx));
    }
}
