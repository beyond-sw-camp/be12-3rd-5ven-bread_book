package com.example.breadbook.domain.order;

import com.example.breadbook.domain.order.model.Order;
import com.example.breadbook.domain.order.model.OrderDto;
import com.example.breadbook.global.response.BaseResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/regist")
    public ResponseEntity<BaseResponse<Order>> regist(OrderDto.OrderDtoReq dto){
        return ResponseEntity.ok(orderService.registOrder(dto));
    }
}
