package com.ssafy.thxstore.reservation.service;

import com.ssafy.thxstore.reservation.dto.ReviewRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Component
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ReviewService {

    public void createReview(ReviewRequest reviewRequest) {
        
    }
}
