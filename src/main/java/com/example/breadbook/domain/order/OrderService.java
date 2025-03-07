package com.example.breadbook.domain.order;

import com.example.breadbook.domain.chat.ChattingRoomRepository;
import com.example.breadbook.domain.chat.model.ChattingRoom;
import com.example.breadbook.domain.chat.model.Participant;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.member.repository.MemberRepository;
import com.example.breadbook.domain.order.model.Order;
import com.example.breadbook.domain.order.model.OrderDto;
import com.example.breadbook.domain.product.ProductStatus;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.product.repository.ProductRepository;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ChattingRoomRepository chattingRoomRepository;
    private final ProductRepository productRepository;

    @Transactional
    public BaseResponse<Order> registOrder(OrderDto.OrderDtoReq dto) {
        System.out.println(dto.getAmount());
        System.out.println(dto.getChattingRoom());
        System.out.println("============================");
        System.out.println("============================");
        System.out.println("============================");System.out.println("============================");


        ChattingRoom chattingRoom = chattingRoomRepository.findByMemberAndProduct(dto.getChattingRoom());

        Order order = new Order();

        for (Participant participant  : chattingRoom.getParticipants()){
            if(participant.getMember().getIdx()==chattingRoom.getBuyer().getIdx()){
                order = OrderDto.OrderRegistResp.toEntity(chattingRoom, dto.getAmount());
            }
        }

        try {
            Order result = orderRepository.save(order);

            try {
                Product product = result.getProduct();
                productUpdate(product);
            } catch (Exception e) {
                throw new RuntimeException("상품 업데이트 실패로 인해 주문을 롤백합니다.", e);
            }

            return new BaseResponse(BaseResponseMessage.ORDER_REGISTER_SUCCESS, result.getIdx());
        } catch (Exception e) {
            return new BaseResponse(BaseResponseMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void productUpdate(Product product) {
        try {
            product.setProductStatus(ProductStatus.거래_예약중);
            productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("상품 업데이트 중 오류 발생", e);
        }
    }


    @Transactional(readOnly = true)
    public BaseResponse<List<OrderDto.PayListResp>> PayList(OrderDto.OrderListReq dto,Member member) {
        Pageable pageable = PageRequest.of(dto.getPage(), 5);
        Page<Product> products = productRepository.findByProductWithMemberPay(member.getIdx(),pageable);


        List<OrderDto.PayListResp> list = products.getContent().stream().map(OrderDto.PayListResp::of).toList();


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
    public BaseResponse<List<OrderDto.OrderListResp>> orderList(OrderDto.OrderListReq dto,Member member) {

        Pageable pageable = PageRequest.of(dto.getPage(), 5);

        Page<Product> products = productRepository.findByProductWithMemberOrder(member.getIdx(),pageable);

        List<OrderDto.OrderListResp> list = products.getContent().stream().map(OrderDto.OrderListResp::of).toList();

        if (list.size() > 0) {
            return new BaseResponse(BaseResponseMessage.ORDER_PAYlISTFIND_SUCCESS, list);
        }

        return new BaseResponse(BaseResponseMessage.INTERNAL_SERVER_ERROR);

    }
}
