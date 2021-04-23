package com.ssafy.thxstore.reservation.repository;

import com.ssafy.thxstore.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
