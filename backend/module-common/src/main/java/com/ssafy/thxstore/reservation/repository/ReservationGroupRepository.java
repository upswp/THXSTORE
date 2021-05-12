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
        " (rg.count,rg.price,rg.productName,rg.userId,rg.reservationStatus)" +
        " from ReservationGroup rg join rg.reservation r " +
        " where rg.userId = :memberId")
    List<ReservationGroup> findReservationlist(Long memberId);


    @Modifying
    @Query(value = "delete from reservation_group where user_id = :memberId and store_id = :storeId",nativeQuery = true)
    void deleteReservation(Long memberId, Long storeId);


//    @Query("select new com.ssafy.thxstore.reservation.domain.ReservationGroup" +
//            " (rg.count,rg.price,rg.productName,rg.userId,rg.reservationStatus)" +
//            " from ReservationGroup " +
//            " where rg.userId = :memberId")
//    Optional<Member> findByIdandStoreId(Long memberId, Long storeId);

    List<ReservationGroup> findAllByUserIdAndStoreId(Long storeId,Long memberId);
}
