package com.example.breadbook.domain.order;

import com.example.breadbook.domain.order.model.Order;
import com.example.breadbook.domain.order.model.OrderDto;
import com.example.breadbook.global.response.BaseResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/regist")
    public ResponseEntity<BaseResponse<Order>> regist(OrderDto.OrderDtoReq dto){
        return ResponseEntity.ok(orderService.registOrder(dto));
    }

    @GetMapping("/orderList/{idx}")
    public ResponseEntity<BaseResponse<List<OrderDto.OrderListResp>>> orderList(@PathVariable Long idx){
        return ResponseEntity.ok(orderService.orderList(idx));
    }

    @GetMapping("/payList/{idx}")
    public ResponseEntity<BaseResponse<List<OrderDto.PayListResp>>> payList(@PathVariable Long idx){
        return ResponseEntity.ok(orderService.PayList(idx));
    }

    @PostMapping("/orderDetails/{idx}")
    public ResponseEntity<BaseResponse<OrderDto.OrderDetailsResp>> orderDetails(@PathVariable Long idx) {
        return ResponseEntity.ok(orderService.orderDetails(idx));
    }

}
