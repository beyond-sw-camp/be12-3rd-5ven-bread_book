package com.example.breadbook.domain.order;


import com.example.breadbook.domain.book.BookRepository;
import com.example.breadbook.domain.member.repository.MemberRepository;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.order.model.Order;
import com.example.breadbook.domain.order.model.OrderDto;
import com.example.breadbook.domain.order.model.OrderStatus;

import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.product.repository.ProductRepository;
import com.example.breadbook.domain.review.ReviewRepository;
import com.example.breadbook.domain.review.model.Review;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public BaseResponse<Order> registOrder(OrderDto.OrderDtoReq dto) {
        Optional<Member> member = memberRepository.findById(dto.getMemberIdx());
        Optional<Product> product = productRepository.findById(dto.getProductIdx());

        if(member.isPresent()&&product.isPresent()) {
            Order order = OrderDto.OrderRegistResp.toEntity(member.get(),product.get(),dto.getAmount());

            orderRepository.save(order);

            return new BaseResponse(BaseResponseMessage.ORDER_REGISTER_SUCCESS,order);
        }

        return new BaseResponse(BaseResponseMessage.INTERNAL_SERVER_ERROR);
    }

    @Transactional(readOnly = true)
    public BaseResponse<List<OrderDto.OrderListResp>> orderList(Long idx) {
        List<Order> orders=orderRepository.findByMember(memberRepository.findById(idx).get());
        List<OrderDto.OrderListResp> list=new ArrayList<>();

        for (Order order : orders) {
            Optional<Review> review=reviewRepository.findByProduct(order.getProduct());
            list.add(OrderDto.OrderListResp.toResp(order,review));
        }

        if(list.size()>0) {
            return new BaseResponse(BaseResponseMessage.ORDER_ORDERlISTFIND_SUCCESS,list);
        }

        return new BaseResponse(BaseResponseMessage.INTERNAL_SERVER_ERROR);
    }

    @Transactional(readOnly = true)
    public BaseResponse<List<OrderDto.PayListResp>> PayList(Long idx) {

        List<Order> orders=orderRepository.findByMember(memberRepository.findById(idx).get());
        List<OrderDto.PayListResp> list=new ArrayList<>();

        for (Order order : orders) {
            list.add(OrderDto.PayListResp.toResp(order));
        }

        if(list.size()>0) {
            return new BaseResponse(BaseResponseMessage.ORDER_PAYlISTFIND_SUCCESS,list);
        }

        return new BaseResponse(BaseResponseMessage.INTERNAL_SERVER_ERROR);
    }


//    @Transactional(readOnly = true)
//    public List<Order> findOrders(Long memberIdx) {
//        return orderRepository.findByMember(memberIdx);
//    }
//
//    @Transactional(readOnly = true)
//    public Order findOderDetails(Long orderIdx) {
//        return orderRepository.findById(orderIdx).orElse(null);
//    }

    public BaseResponse<OrderDto.OrderDetailsResp> orderDetails(Long idx) {
        Order order=orderRepository.findById(idx).get();
        Optional<Review> review=reviewRepository.findByProduct(order.getProduct());
        OrderDto.OrderDetailsResp result= OrderDto.OrderDetailsResp.toResp(order,review);
        return new BaseResponse(BaseResponseMessage.ORDER_ORDERDETAILS_SUCCESS,result);
    }
}
