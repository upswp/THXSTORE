package com.ssafy.thxstore.reservation.dto;

import com.ssafy.thxstore.reservation.domain.ReservationStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationDto {
    @NotBlank
    private Long userId;

    @NotBlank
    private String email;

    @NotBlank
    private Long productId;

    @NotBlank
    private String productname;

    @NotBlank
    private int price;  //편의를 위해

    @NotBlank
    private int count;

    @NotBlank
    private Long storeId;

    @NotBlank
    private ReservationStatus reservationStatus;

    @Builder
    public ReservationDto(String email,Long storeId, Long userId, Long productId, String productname, int price, int count,ReservationStatus reservationStatus) {
        this.userId = userId;
        this.storeId = storeId;
        this.productId = productId;
        this.productname =productname;
        this.price = price;
        this.count = count;
        this.reservationStatus = reservationStatus;
        this.email = email;
    }
}