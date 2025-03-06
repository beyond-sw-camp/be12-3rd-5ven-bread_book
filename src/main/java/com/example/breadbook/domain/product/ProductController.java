package com.example.breadbook.domain.product;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.ProductDto;
import com.example.breadbook.domain.product.service.ProductService;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    /* @RequestPart와 @RequestBody의 차이??*/

    @PostMapping("register")
    public ResponseEntity<BaseResponse<ProductDto.ProductResponse>> register(@AuthenticationPrincipal Member member,
                                                                             @RequestPart ProductDto.RegisterRequest dto,
                                                                             @RequestPart MultipartFile[] imgFiles) {
        ProductDto.ProductResponse response = productService.registerProduct(dto, member, imgFiles);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.REQUEST_SUCCESS, response));
    }

    @GetMapping("/{productIdx}")
    public ResponseEntity<BaseResponse<ProductDto.ProductResponse>> readItem(@PathVariable Long productIdx) {
        ProductDto.ProductResponse response = productService.getProductItem(productIdx);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.REQUEST_SUCCESS, response));
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponse<Page<ProductDto.ListResponse>>> list(
            @AuthenticationPrincipal Member member,
            @PageableDefault(size = 24, sort = "price", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<ProductDto.ListResponse> response = productService.getProductList(member, pageable);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.REQUEST_SUCCESS,response));
    }

    @DeleteMapping("/delete/{productIdx}")
    public ResponseEntity<BaseResponse<ProductDto.DeleteResponse>> deleteItem(@PathVariable Long productIdx, @AuthenticationPrincipal Member member) throws Exception {
        ProductDto.DeleteResponse response = productService.deleteProduct(productIdx, member);

        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.REQUEST_SUCCESS, response));
    }




}