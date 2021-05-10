package com.ssafy.thxstore.reservation.repository;

import com.ssafy.thxstore.reservation.domain.Cart;
import com.ssafy.thxstore.reservation.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
