package com.example.breadbook.domain.order;



import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.member.repository.MemberRepository;
import com.example.breadbook.domain.order.model.Order;
import com.example.breadbook.domain.order.model.OrderDto;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public BaseResponse<Order> registOrder(OrderDto.OrderDtoReq dto) {
        Member member = Member.builder()
                .idx(dto.getMemberIdx())
                .build();

        Product product = Product.builder().idx(dto.getProductIdx()).build();

        Order order = OrderDto.OrderRegistResp.toEntity(member, product, dto.getAmount());

        try{
            orderRepository.save(order);

            return new BaseResponse(BaseResponseMessage.ORDER_REGISTER_SUCCESS, order);
        } catch (Exception e) {
            return new BaseResponse(BaseResponseMessage.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional(readOnly = true)
    public BaseResponse<List<OrderDto.PayListResp>> PayList(Long idx) {
        List<Member> members = memberRepository.findMemberWithOrdersAndProducts(idx);
        List<OrderDto.PayListResp> list = new ArrayList<>();

        for(Order order : members.get(0).getOrders()) {

            list.add(OrderDto.PayListResp.toResp(order));
        }

        if (list.size() > 0) {
            return new BaseResponse(BaseResponseMessage.ORDER_PAYlISTFIND_SUCCESS, list);
        }

        return new BaseResponse(BaseResponseMessage.INTERNAL_SERVER_ERROR);
    }


    public BaseResponse<OrderDto.OrderDetailsResp> orderDetails(Long idx) {
        Order order = orderRepository.findByOrderDetailse(idx);

        OrderDto.OrderDetailsResp result = OrderDto.OrderDetailsResp.toResp(order);
        return new BaseResponse(BaseResponseMessage.ORDER_ORDERDETAILS_SUCCESS, result);
    }

    @Transactional(readOnly = true)
    public BaseResponse<List<OrderDto.OrderListResp>> orderList(Long idx) {

        List<Member> members = memberRepository.findMemberWithOrdersAndProducts(idx);


        List<OrderDto.OrderListResp> list = new ArrayList<>();
        for (Order order : members.get(0).getOrders()) {
            list.add(OrderDto.OrderListResp.toResp(order));

        }


//        List<OrderDto.test> orderListResps = members.stream()
//                .flatMap(member -> member.getProducts().stream() // 주문 목록을 먼저 순회
//                                .flatMap(product -> product.getReviews().stream() // 상품에 대한 리뷰 목록을 순회
//                                        .flatMap(order -> product.getProductImageList().stream() // 상품 이미지 목록을 순회
//                                                .map(product -> OrderDto.test.builder()
//                                                        .memberIdx(member.getIdx())
//                                                        .productIdx(product.getIdx())
//                                                                .bookIdx(product.getBook().getIdx())
//                                                                .orderIdx(orders.getIdx())
//                                                                .productImageListIdx(productImage.getIdx())
//                                                        .build()
//                                                )
//                                        )
//                               )
//                        )
//                )
//                .collect(Collectors.toList());

        if (list.size() > 0) {
            return new BaseResponse(BaseResponseMessage.ORDER_PAYlISTFIND_SUCCESS, list);
        }

        return new BaseResponse(BaseResponseMessage.INTERNAL_SERVER_ERROR);

    }
}
