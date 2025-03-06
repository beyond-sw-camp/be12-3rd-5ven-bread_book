package com.example.breadbook.domain.order;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.order.model.Order;
import com.example.breadbook.domain.order.model.OrderDto;
import com.example.breadbook.global.response.BaseResponse;
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
    public ResponseEntity<BaseResponse<Order>> regist(@RequestBody OrderDto.OrderDtoReq dto){
        return ResponseEntity.ok(orderService.registOrder(dto));
    }

    @PostMapping("/orderList")
    public ResponseEntity<BaseResponse<List<OrderDto.OrderListResp>>> orderList(@RequestBody OrderDto.OrderListReq dto,@AuthenticationPrincipal Member member){
        return ResponseEntity.ok(orderService.orderList(dto,member));
    }

    @PostMapping("/payList")
    public ResponseEntity<BaseResponse<List<OrderDto.PayListResp>>> payList(@RequestBody OrderDto.OrderListReq dto,@AuthenticationPrincipal Member member){
        return ResponseEntity.ok(orderService.PayList(dto,member));
    }

    @PostMapping("/orderDetails/{idx}")
    public ResponseEntity<BaseResponse<OrderDto.OrderDetailsResp>> orderDetails(@PathVariable Long idx) {
        return ResponseEntity.ok(orderService.orderDetails(idx));
    }
}