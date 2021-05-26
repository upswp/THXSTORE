package com.ssafy.thxstore.reservation.repository;

import com.ssafy.thxstore.reservation.domain.Answer;
import com.ssafy.thxstore.reservation.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByReviewId(Long id);
}
