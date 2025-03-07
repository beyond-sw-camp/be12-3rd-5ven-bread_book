package com.example.breadbook.domain.order;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.order.model.Order;
import com.example.breadbook.domain.order.model.OrderDto;
import com.example.breadbook.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/regist")
    @Operation(summary = "주문 등록", description = "주문을 등록하는 기능입니다.")
    public ResponseEntity<BaseResponse<Order>> regist(@RequestBody OrderDto.OrderDtoReq dto){
        return ResponseEntity.ok(orderService.registOrder(dto));
    }

    @PostMapping("/orderList")
    @Operation(summary = "구매 내역", description = "상품 구매 내역입니다.")
    public ResponseEntity<BaseResponse<List<OrderDto.OrderListResp>>> orderList(@RequestBody OrderDto.OrderListReq dto,@AuthenticationPrincipal Member member){
        return ResponseEntity.ok(orderService.orderList(dto,member));
    }

    @PostMapping("/payList")
    @Operation(summary = "판매 목록", description = "상품 판매 목록입니다.")
    public ResponseEntity<BaseResponse<List<OrderDto.PayListResp>>> payList(@RequestBody OrderDto.OrderListReq dto,@AuthenticationPrincipal Member member){
        return ResponseEntity.ok(orderService.PayList(dto,member));
    }

    @PostMapping("/orderDetails/{idx}")
    @Operation(summary = "주문 상세", description = "주문 정보를 확인하는 기능입니다.")
    public ResponseEntity<BaseResponse<OrderDto.OrderDetailsResp>> orderDetails(@PathVariable Long idx) {
        return ResponseEntity.ok(orderService.orderDetails(idx));
    }
}