package com.ssafy.thxstore.reservation.repository;

import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.dto.CartDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select new com.ssafy.thxstore.reservation.domain.Reservation" +
            " (c.member,c.cart,current_date,DEFAULT,c.storeId)" +
            " from Cart c" +
            " where c.member.id = :memberId")
    List<Reservation> findReservationlist(Long memberId);
}
