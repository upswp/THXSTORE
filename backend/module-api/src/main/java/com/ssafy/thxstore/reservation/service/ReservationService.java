package com.ssafy.thxstore.reservation.service;

import com.ssafy.thxstore.reservation.dto.ReservationDto;
import com.ssafy.thxstore.reservation.dto.request.StatusRequest;

import java.text.ParseException;
import java.util.List;

public interface ReservationService {
    String addReservation(String email,ReservationDto reservationList);
    List<ReservationDto> getReservation(String email,String type) throws ParseException;
    String deleteReservation(String email,Long Id,String type);
    String statusUpdate(String email, StatusRequest status);
//    void addOrder(Long memberId);
//    List<ReservationDto> getOrder(Long memberId);
}
