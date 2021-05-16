package com.ssafy.thxstore.reservation.repository;

import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.domain.ReservationGroup;
import com.ssafy.thxstore.reservation.domain.ReservationStatus;
import com.ssafy.thxstore.reservation.domain.Review;
import com.ssafy.thxstore.reservation.dto.ReservationDto;
import com.ssafy.thxstore.reservation.dto.StatusRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {


//    @Query("select new com.ssafy.thxstore.reservation.domain.ReservationGroup" +
//        " (r,r.reservationGroup.count,r.reservationGroup.price,r.reservationGroup.productName,r.reservationGroup.userId,r.reservationStatus)" +
//        " from Reservation r join r.reservationGroup rg " +
//        " where m.id = :memberId")
//    List<ReservationGroup> findReservationlist(Long memberId);

//    @Query("select new com.ssafy.thxstore.reservation.dto.ReservationDto" +
//            " (r.member.email, r.storeId,r.member.id,r.product.id,r.product.name,r.price,r.count,r.reservationStatus)" +
//            " from Reservation r join r.member m join r.product p" +
//            " where m.id = :memberId")
//    List<ReservationDto> findgetReservationlist(Long memberId);

//    @Modifying
//    @Transactional
//    @Query(value = "delete from reservation where member_id = :memberId and store_id = :storeId",nativeQuery = true)
//    void deleteReservation(Long memberId, Long storeId);
//
    @Modifying
    @Transactional
    @Query(value = "update reservation set status = :newStatus where email = :email and store_id = :storeId",nativeQuery = true)
    void findReservation(String email, Long storeId , String newStatus);

    @Query(value = "select * from reservation where review_id = :reviewId",nativeQuery = true)
    Reservation findByreviewId(Long reviewId);

//    @Query("select all" +
//            " from Reservation r " +
//            " where rg.userId = :Id")
    @Query(value = "select * from reservation where email = :email",nativeQuery = true)
    List<Reservation> findReservationByMemberId(String email);

//    @Query("select all" +
//            " from Reservation r " +
//            " where rg.storeId = :Id")
    @Query(value = "select * from reservation where store_id = :id",nativeQuery = true)
    List<Reservation> findReservationByStoreId(Long id);

    Optional<Reservation> findByMember(Long memberId);
}
