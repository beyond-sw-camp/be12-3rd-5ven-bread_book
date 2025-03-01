package com.example.breadbook.domain.product;


import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.product.model.ProductDto;
import com.example.breadbook.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    /* @RequestPart와 @RequestBody의 차이??*/

    @PostMapping("register")
    public ResponseEntity<ProductDto.ProductResponse> register(@AuthenticationPrincipal Member member,
                                            @RequestPart ProductDto.ProductRegister dto,
                                            @RequestPart MultipartFile[] imgFiles) {
        ProductDto.ProductResponse response = productService.registerProduct(dto, member, imgFiles);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/list")
//    public ResponseEntity<List<ProductDto>> list() {
//        List<ProductDto.ProductResponse> response = productService.list();
//    }

}
