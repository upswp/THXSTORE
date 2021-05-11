package com.ssafy.thxstore.reservation.service;

import com.ssafy.thxstore.reservation.domain.ReservationStatus;
import com.ssafy.thxstore.reservation.dto.ReservationDto;
import com.ssafy.thxstore.reservation.dto.StatusRequest;

import java.util.List;

public interface ReservationService {
    void addReservation(List<ReservationDto> reservationList);
    List<ReservationDto> getReservation(Long memberId);
    void deleteReservation(Long memberId,Long storeId);
    void statusUpdate(Long memberId, StatusRequest status);


//    void addOrder(Long memberId);
//    List<ReservationDto> getOrder(Long memberId);
}
