package com.example.breadbook.domain.order;


import com.example.breadbook.domain.member.MemberRepository;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.order.model.Order;
import com.example.breadbook.domain.order.model.OrderDto;
import com.example.breadbook.domain.order.model.OrderStatus;
import com.example.breadbook.domain.product.ProductRepository;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public BaseResponse<Order> registOrder(OrderDto.OrderDtoReq dto) {
        Optional<Member> member = memberRepository.findById(dto.getMemberIdx());
        Optional<Product> product = productRepository.findById(dto.getProductIdx());

        if(member.isPresent()&&product.isPresent()) {
            Order order = Order.builder()
                    .orderStatus(OrderStatus.품절)
                    .member(member.get())
                    .product(product.get())
                    .amount(dto.getAmount())
                    .createdAt(LocalDateTime.now())
                    .build();
            orderRepository.save(order);

            return new BaseResponse(BaseResponseMessage.ORDER_REGISTER_SUCCESS,order);
        }

        return new BaseResponse(BaseResponseMessage.INTERNAL_SERVER_ERROR);
    }

    @Transactional(readOnly = true)
    public List<Order> findOrders(Long memberIdx) {
        return orderRepository.findByMember(memberIdx);
    }

    @Transactional(readOnly = true)
    public Order findOderDetails(Long orderIdx) {
        return orderRepository.findById(orderIdx).orElse(null);
    }
}
