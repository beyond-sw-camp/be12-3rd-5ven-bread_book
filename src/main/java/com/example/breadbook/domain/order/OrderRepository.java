package com.example.breaadbook.domain.order;

import com.example.breaadbook.domain.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
