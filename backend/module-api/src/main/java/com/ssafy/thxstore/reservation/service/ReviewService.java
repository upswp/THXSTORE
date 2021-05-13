package com.ssafy.thxstore.reservation.service;

import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.domain.ReservationGroup;
import com.ssafy.thxstore.reservation.domain.Review;
import com.ssafy.thxstore.reservation.dto.ReservationGroupDto;
import com.ssafy.thxstore.reservation.dto.ReviewDto;
import com.ssafy.thxstore.reservation.repository.ReservationRepository;
import com.ssafy.thxstore.reservation.repository.ReviewRepository;
import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.repository.StoreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Component
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;

    public Review createReview(ReviewDto reviewDto) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT);
        String time = timeFormat.format(new Date());

        Optional<Reservation> reservation = reservationRepository.findById(reviewDto.getReservationId());
        Optional<Store> store = storeRepository.findById(reviewDto.getStoreId());
        System.out.println("reviewDto.getStoreId(): " +reviewDto.getStoreId() +reviewDto.getStoreId().getClass().getName());
        System.out.println("asdfasdfwegew"+store);
        Review review = Review.builder().
                dateTime(dateFormat.format(DateTime.now().toDate()) + " " + time).
                comment(reviewDto.getComment()).
                star(reviewDto.getStar()).
                memberId(reviewDto.getMemberId()).
                storeName(store.get().getName()).
                storeId(reservation.get().getStoreId()).
                build();

        reservation.get().setReview(review);

        reviewRepository.save(review);

        return review;
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);

        //연관관계 해제
        Reservation reservation = reservationRepository.findByreviewId(reviewId);
        reservation.deleteReview();

        reviewRepository.delete(review.get());
    }

    public void updateReview(Long reviewId, ReviewDto reviewDto) {
        reviewRepository.updateReview(reviewId, reviewDto.getComment(), reviewDto.getStar());
    }

    @Transactional
    public List<ReviewDto> getReview(Long Id,String type) {
        List<Review> ReviewList;
        List<ReviewDto> ReviewDtoList = new LinkedList<>();

        if(type == "member") {
            ReviewList = reviewRepository.findReviewByMemberId(Id);
        }else{
            ReviewList = reviewRepository.findReviewByStoreId(Id);
        }

        //reservation id 찾아오려면 쿼리 하나 날려야됨 일단 null 보내고 필요하면 넣자
        for(int i =0 ;i<ReviewList.size(); i++){
            ReviewDto reviewDto = ReviewDto.builder().
                    storeId(ReviewList.get(i).getStoreId()).
                    memberId(ReviewList.get(i).getMemberId()).
                    storeName(ReviewList.get(i).getStoreName()).
                    star(ReviewList.get(i).getStar()).
                    dateTime(ReviewList.get(i).getDateTime()).
                    comment(ReviewList.get(i).getComment()).
                    build();

            ReviewDtoList.add(reviewDto);
        }
        return ReviewDtoList;
    }
}
