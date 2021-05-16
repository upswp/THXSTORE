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
        " (rg.reservation,rg.count,rg.price,rg.productName,rg.userId,rg.reservation.reservationStatus,rg.rate)" +
        " from ReservationGroup rg join rg.reservation r" +
                " on rg.reservation.id = rg.reservation" +
        " where rg.reservation.id = :id")
    List<ReservationGroup> findReservationlistByMemberId(Long id);

    @Query("select new com.ssafy.thxstore.reservation.domain.ReservationGroup" +
            " (rg.reservation,rg.count,rg.price,rg.productName,rg.userId,rg.reservation.reservationStatus,rg.rate)" +
            " from ReservationGroup rg join rg.reservation r " +
            " on rg.reservation.id = rg.reservation" +
            " where rg.reservation.id = :Id")
    List<ReservationGroup> findReservationlistByStoreId(Long Id);

//    @Query("select new com.ssafy.thxstore.reservation.domain.ReservationGroup" +
//            " (rg.count,rg.price,rg.productName,rg.userId,rg.reservationStatus)" +
//            " from ReservationGroup " +
//            " where rg.userId = :memberId")
//    Optional<Member> findByIdandStoreId(Long memberId, Long storeId);


    @Query(value = "select * from reservation_group rg where user_id = :Id and store_id = :storeId",nativeQuery = true)
    List<ReservationGroup> findAllByMemberIdAndStoreId(Long Id, Long storeId);
}
