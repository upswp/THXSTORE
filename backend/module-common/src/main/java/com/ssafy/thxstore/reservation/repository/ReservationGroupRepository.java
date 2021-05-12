package com.ssafy.thxstore.reservation.repository;

import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.domain.ReservationGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationGroupRepository extends JpaRepository<ReservationGroup, Long> {

        @Query("select new com.ssafy.thxstore.reservation.domain.ReservationGroup" +
        " (rg.count,rg.price,rg.productName,rg.userId,rg.reservationStatus)" +
        " from ReservationGroup rg join rg.reservation r " +
        " where rg.userId = :memberId")
    List<ReservationGroup> findReservationlist(Long memberId);

}
