package com.example.breadbook.domain.review;

import com.example.breadbook.domain.review.model.Review;
import com.example.breadbook.domain.review.model.ReviewDto;
import com.example.breadbook.global.response.BaseResponse;
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
    public ResponseEntity<BaseResponse<Review>> registerReview(@RequestBody ReviewDto.ReviewDtoReq dto) {
        return ResponseEntity.ok(reviewService.regist(dto));
    }

    @GetMapping("/find/{idx}")
    public ResponseEntity<BaseResponse<List<Review>>> reviewFind(@PathVariable Long memberIdx) {
        return ResponseEntity.ok(reviewService.findReview(memberIdx));
    }
}
