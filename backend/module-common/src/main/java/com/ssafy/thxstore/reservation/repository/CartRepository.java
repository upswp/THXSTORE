package com.ssafy.thxstore.reservation.repository;

import com.ssafy.thxstore.reservation.domain.Cart;
import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.dto.CartDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {


    @Query("select new com.ssafy.thxstore.reservation.dto.CartDto" +
            " (m.id,p.id,c.productName,c.count,c.price)" +
            " from Cart c join c.member m join c.product p" +
            " where m.id = :memberId")
    List<CartDto> findCartlist(Long memberId);

}
