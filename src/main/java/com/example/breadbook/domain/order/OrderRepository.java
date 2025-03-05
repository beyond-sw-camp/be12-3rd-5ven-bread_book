package com.example.breadbook.domain.order;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByMember(Member member);

    @Query("SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.product p " +
            "LEFT JOIN FETCH o.review r " +
            "LEFT JOIN FETCH p.book b " +
            "LEFT JOIN FETCH p.productImageList i " +
            "LEFT JOIN FETCH p.category c " +
            "LEFT JOIN FETCH p.member m " +
            "WHERE o.idx = :idx")
    Order findByOrderDetailse(Long idx);

    @Query("SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.product p " +
            "LEFT JOIN FETCH p.member m " +
            "LEFT JOIN FETCH o.review r " +
            "LEFT JOIN FETCH p.category c " +
            "LEFT JOIN FETCH p.book b " +
            "WHERE o.idx = :idx")
    Order findByMemberAndProduct(Long idx);
}
