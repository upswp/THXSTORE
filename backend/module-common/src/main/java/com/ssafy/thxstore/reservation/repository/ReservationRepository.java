package com.ssafy.thxstore.reservation.repository;

import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.dto.ReservationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {


//    @Query("select new com.ssafy.thxstore.reservation.dto.ReservationDto" +
//            " (c.storeId,m.id,p.id,c.productName,c.count,c.price)" +
//            " from Reservation r join c.member m join c.product p" +
//            " where m.id = :memberId")
//    List<ReservationDto> findCartlist(Long memberId);




//    @Query(" select new com.ssafy.thxstore.reservation.dto.ReservationDto" +
//            " (m.id)"+
//            " from Reservation r"+
//            " where r.id = :memberId")
//    List<ReservationDto> findOrderById(Long memberId);


}
