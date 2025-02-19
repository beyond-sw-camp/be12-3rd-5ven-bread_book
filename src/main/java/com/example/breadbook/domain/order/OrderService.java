package com.example.breaadbook.domain.order;

import com.example.breaadbook.domain.order.model.Order;
import com.example.breaadbook.domain.order.model.OrderDto;
import com.example.breaadbook.domain.order.model.OrderStatus;
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
    public Order createOrder(OrderDto.OrderDtoReq dto) {
        Optional<Member> member = memberRepository.findById(dto.getMemberIdx());
        Optional<Product> product = productRepository.findById(dto.getProductIdx());

        if(member.isPresent()&&product.isPresent()) {
            return  orderRepository.save(Order.builder()
                    .orderStatus(OrderStatus.거래중)
                    .member(member.get())
                    .product(product.get())
                    .amount(dto.getAmount())
                    .createdAt(LocalDateTime.now())
                    .build());

        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Order> findOrders(Long memberIdx) {
        return orderRepository.findByMember(memberIdx).orElse(null);
    }

    @Transactional(readOnly = true)
    public Order findOderDetails(Long orderIdx) {
        return orderRepository.findById(orderIdx).orElse(null);
    }
}
