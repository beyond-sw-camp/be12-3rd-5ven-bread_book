package com.example.breadbook.domain.order;

import com.example.breadbook.domain.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByMember(Long memberIdx);
}
