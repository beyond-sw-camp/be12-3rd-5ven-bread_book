package com.example.breadbook.domain.order;



import com.example.breadbook.domain.chat.ChattingRoomRepository;
import com.example.breadbook.domain.chat.model.ChattingRoom;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.member.repository.MemberRepository;
import com.example.breadbook.domain.order.model.Order;
import com.example.breadbook.domain.order.model.OrderDto;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.product.repository.ProductRepository;
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
    private final ChattingRoomRepository chattingRoomRepository;
    private final ProductRepository productRepository;

//    @Transactional
//    public BaseResponse<Order> registOrder(OrderDto.OrderDtoReq dto) {
//        List<ChattingRoom> chattingRoom = chattingRoomRepository.findByMemberAndProduct(dto.getChattingRoom());
//
//        Order order = new Order();
//        for (Member member  : chattingRoom.getParticipant().getMember()){
//            if(member.getIdx()!=chattingRoom.get(0).getProduct.getIdx()){
//                order = OrderDto.OrderRegistResp.toEntity(member, chattingRoom.get(0).getProduct, dto.getAmount());
//            }
//        }
//
//        try{
//            orderRepository.save(order);
//
//            return new BaseResponse(BaseResponseMessage.ORDER_REGISTER_SUCCESS, order);
//        } catch (Exception e) {
//            return new BaseResponse(BaseResponseMessage.INTERNAL_SERVER_ERROR);
//        }
//    }


    @Transactional(readOnly = true)
    public BaseResponse<List<OrderDto.PayListResp>> PayList(Long idx) {
        List<Member> members = memberRepository.findMemberWithProductsAndOrders(idx);
        List<OrderDto.PayListResp> list = new ArrayList<>();

        for(Product product : members.get(0).getProducts()) {
            list.add(OrderDto.PayListResp.of(product));
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
            list.add(OrderDto.OrderListResp.of(order));

        }

        if (list.size() > 0) {
            return new BaseResponse(BaseResponseMessage.ORDER_PAYlISTFIND_SUCCESS, list);
        }

        return new BaseResponse(BaseResponseMessage.INTERNAL_SERVER_ERROR);

    }
}
