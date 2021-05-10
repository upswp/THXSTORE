package com.ssafy.thxstore.reservation.service;

import com.ssafy.thxstore.product.dto.ProductDto;
import com.ssafy.thxstore.reservation.domain.Cart;
import com.ssafy.thxstore.reservation.dto.CartDto;
import com.ssafy.thxstore.reservation.dto.OrderRequest;
import com.ssafy.thxstore.reservation.dto.ReservationDto;

import java.util.List;

public interface ReservationService {
    void addCart(List<CartDto> cartList);
    List<CartDto> getCart(Long memberId);
    void addOrder(OrderRequest orderRequest);

    List<ReservationDto> getOrder(Long memberId);
}
