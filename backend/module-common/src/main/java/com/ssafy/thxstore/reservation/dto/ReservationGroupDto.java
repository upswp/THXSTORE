package com.ssafy.thxstore.reservation.dto;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.domain.ReservationStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationGroupDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private int price;

    @NotBlank
    private int count;

    @NotBlank
    private String productName;

    @NotBlank
    private ReservationStatus reservationStatus;

    @Builder
    public ReservationGroupDto(Long id, Long userId, int price,int count,String productName,Reservation reservation,ReservationStatus reservationStatus) {

        this.userId = userId;
        this.price = price;
        this.count = count;
        this.productName = productName;
        this.reservationStatus = reservationStatus;
    }
}
