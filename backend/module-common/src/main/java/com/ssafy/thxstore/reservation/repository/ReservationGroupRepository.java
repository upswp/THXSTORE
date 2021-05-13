package com.ssafy.thxstore.reservation.repository;

import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.domain.ReservationGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ReservationGroupRepository extends JpaRepository<ReservationGroup, Long> {

        @Query("select new com.ssafy.thxstore.reservation.domain.ReservationGroup" +
        " (rg.reservation,rg.count,rg.price,rg.productName,rg.userId,rg.reservation.reservationStatus)" +
        " from ReservationGroup rg join rg.reservation r " +
        " where rg.userId = :Id")
    List<ReservationGroup> findReservationlistByMemberId(Long Id);

    @Query("select new com.ssafy.thxstore.reservation.domain.ReservationGroup" +
            " (rg.reservation,rg.count,rg.price,rg.productName,rg.userId,rg.reservation.reservationStatus)" +
            " from ReservationGroup rg join rg.reservation r " +
            " where rg.storeId = :Id")
    List<ReservationGroup> findReservationlistByStoreId(Long Id);

//    @Query("select new com.ssafy.thxstore.reservation.domain.ReservationGroup" +
//            " (rg.count,rg.price,rg.productName,rg.userId,rg.reservationStatus)" +
//            " from ReservationGroup " +
//            " where rg.userId = :memberId")
//    Optional<Member> findByIdandStoreId(Long memberId, Long storeId);

    List<ReservationGroup> findAllByUserIdAndStoreId(Long memberId,Long storeId);
}
