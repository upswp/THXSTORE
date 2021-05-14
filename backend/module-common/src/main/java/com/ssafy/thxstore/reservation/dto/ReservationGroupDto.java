package com.ssafy.thxstore.reservation.dto;

import com.ssafy.thxstore.common.ColumnDescription;
import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.domain.ReservationStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationGroupDto {

//    private Long userId;

    private int price;

    private int count;

    private int rate;

//    private String orderTime;

    private String productName;

//    private ReservationStatus reservationStatus;

    @Builder
    public ReservationGroupDto(int rate,Long id, Long userId, int price,int count,String productName,Reservation reservation,ReservationStatus reservationStatus,String orderTime) {
        this.rate = rate;
//        this.orderTime = orderTime;
//        this.userId = userId;
        this.price = price;
        this.count = count;
        this.productName = productName;
//        this.reservationStatus = reservationStatus;
    }
}
