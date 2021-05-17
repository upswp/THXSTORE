package com.ssafy.thxstore.reservation.dto;

import com.ssafy.thxstore.reservation.domain.ReservationStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationDto implements Comparable<ReservationDto>{
//    @NotBlank
    private Long userId;

    private Long reservationId;

    private String nickname;

//    @NotBlank
    private Long storeId;

    //    @NotBlank
    private String orderTime;

    private String email;

    private String storeName;

    private String storeImg;

//    @NotBlank
    private ReservationStatus reservationStatus;

//    @NotBlank
    private List<ReservationGroupDto> reservationGroups;

    @Builder
    public ReservationDto(Long reservationId,String storeImg,String storeName,String email,String nickname,String orderTime,Long storeId, Long userId, List<ReservationGroupDto> reservationGroups, ReservationStatus reservationStatus) {
        this.userId = userId;
        this.email = email;
        this.reservationId = reservationId;
        this.storeImg = storeImg;
        this.storeName =storeName;
        this.nickname =nickname;
        this.orderTime = orderTime;
        this.storeId = storeId;
        this.reservationGroups = reservationGroups;
        this.reservationStatus = reservationStatus;
    }

    public void updateOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public int compareTo(ReservationDto arg0) {
        // TODO Auto-generated method stub
        Long target = arg0.getReservationId();
        if(reservationId == target) return 0;
        else if(reservationId > target) return 1;
        else return -1;
    }
}