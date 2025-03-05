package com.example.breadbook.domain.wish;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wish")
@RequiredArgsConstructor
public class WishController {
    private final WishService wishService;

    @PostMapping("/toggle/{productId}")
    public ResponseEntity<Void> toggleWish(@PathVariable Long productId, @AuthenticationPrincipal Member member) {
        wishService.toggleWish(productId, member);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> getWishList(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(wishService.getWishList(member));
    }
}