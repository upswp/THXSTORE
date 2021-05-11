package com.ssafy.thxstore.reservation.repository;

import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.domain.ReservationStatus;
import com.ssafy.thxstore.reservation.dto.ReservationDto;
import com.ssafy.thxstore.reservation.dto.StatusRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select new com.ssafy.thxstore.reservation.dto.ReservationDto" +
            " (r.member.email, r.storeId,r.member.id,r.product.id,r.product.name,r.price,r.count,r.reservationStatus)" +
            " from Reservation r join r.member m join r.product p" +
            " where m.id = :memberId")
    List<ReservationDto> findgetReservationlist(Long memberId);

    @Modifying
    @Transactional
    @Query(value = "delete from reservation where member_id = :memberId and store_id = :storeId",nativeQuery = true)
    void deleteReservation(Long memberId, Long storeId);

    @Modifying
    @Transactional
    @Query(value = "update reservation set status = :newStatus where member_id = :memberId and store_id = :storeId",nativeQuery = true)
    void findReservation(Long memberId, Long storeId , String newStatus);
}
