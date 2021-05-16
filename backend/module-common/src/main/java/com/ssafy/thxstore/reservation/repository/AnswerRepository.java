package com.ssafy.thxstore.reservation.repository;

import com.ssafy.thxstore.reservation.domain.Answer;
import com.ssafy.thxstore.reservation.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
