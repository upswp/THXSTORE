package com.ssafy.thxstore.reservation.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class ReservationDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private List<CartDto> cartList;

    @Builder
    public ReservationDto(Long userId, List<CartDto> cartList) {
        this.userId = userId;
        this.cartList = cartList;
    }
}
