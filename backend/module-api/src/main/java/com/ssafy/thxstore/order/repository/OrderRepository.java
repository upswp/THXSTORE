package com.ssafy.thxstore.order.repository;

import com.ssafy.thxstore.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
