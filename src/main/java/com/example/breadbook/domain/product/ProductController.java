package com.example.breadbook.domain.product;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.ProductDto;
import com.example.breadbook.domain.product.service.ProductService;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "중고책 상품 관리 기능")
@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    /* @RequestPart와 @RequestBody의 차이??*/
    /* multipart-formdata 와 그냥 raw http body*/

    @Operation(summary = "상품 등록", description = "판매할 중고책을 등록하는 기능이다.")
    @PostMapping("register")
    public ResponseEntity<BaseResponse<ProductDto.ProductResponse>> register(@AuthenticationPrincipal Member member,
                                                                             @RequestPart ProductDto.RegisterRequest dto,
                                                                             @RequestPart MultipartFile[] imgFiles) {
        ProductDto.ProductResponse response = productService.registerProduct(dto, member, imgFiles);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.REQUEST_SUCCESS, response));
    }

    @Operation(summary = "개별 상품 상세 조회", description = "중고책 상품 하나의 상세한 판매 정보를 조회하는 기능이다.")
    @GetMapping("/{productIdx}")
    public ResponseEntity<BaseResponse<ProductDto.ProductResponse>> readItem(@PathVariable Long productIdx) {
        ProductDto.ProductResponse response = productService.getProductItem(productIdx);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.REQUEST_SUCCESS, response));
    }

    @Operation(summary = "상품 목록 조회", description = "상품의 간결한 정보를 표시하여 목록을 기능이다. 판매자 등급, 가격, 책 상태 등으로 정렬할 수 있다. ")
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<Page<ProductDto.ListResponse>>> list(
            @AuthenticationPrincipal Member member,
            @PageableDefault(size = 16, sort = "price", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<ProductDto.ListResponse> response = productService.getProductList(member, pageable);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.REQUEST_SUCCESS,response));
    }

    @Operation(summary = "상품 삭제 기능", description = "상품을 등록한 사용자가 상품을 삭제할 수 있도록 하는 기능이다.")
    @DeleteMapping("/delete/{productIdx}")
    public ResponseEntity<BaseResponse<ProductDto.DeleteResponse>> deleteItem(@PathVariable Long productIdx, @AuthenticationPrincipal Member member) throws Exception {
        ProductDto.DeleteResponse response = productService.deleteProduct(productIdx, member);

        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.REQUEST_SUCCESS, response));
    }

    @Operation(summary = "상품 수정 기능", description = "상품을 등록한 사용자가 상품의 내용을 수정할 수 있도록 하는 기능이다. 단 상품 사진은 수정할 수 없다.")
    @PostMapping("/update/{productIdx}")
    public ResponseEntity<BaseResponse<ProductDto.ProductResponse>> updateItem(@PathVariable Long productIdx,
                                                                               @AuthenticationPrincipal Member member,
                                                                               @RequestBody ProductDto.RegisterRequest dto) throws Exception {
        // 사진 수정 기능 어떻게 구현할지 고민해야함.
        ProductDto.ProductResponse response = productService.updateProduct(productIdx, member, dto);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.REQUEST_SUCCESS, response));
    }




}