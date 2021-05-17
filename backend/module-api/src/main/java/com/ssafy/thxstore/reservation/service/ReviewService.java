package com.ssafy.thxstore.reservation.service;

import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.repository.MemberRepository;
import com.ssafy.thxstore.reservation.domain.Answer;
import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.domain.Review;
import com.ssafy.thxstore.reservation.dto.request.AnswerRequest;
import com.ssafy.thxstore.reservation.dto.ReviewDto;
import com.ssafy.thxstore.reservation.dto.response.CheckReviewResponse;
import com.ssafy.thxstore.reservation.repository.AnswerRepository;
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
    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;

    public Review createReview(ReviewDto reviewDto) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT);
        String time = timeFormat.format(new Date());

        Optional<Reservation> reservation = reservationRepository.findById(reviewDto.getReservationId());
        Optional<Store> store = storeRepository.findById(reviewDto.getStoreId());
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
    public CheckReviewResponse deleteReview(Long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);

        //연관관계 해제
        Reservation reservation = reservationRepository.findByreviewId(reviewId);
        reservation.deleteReview();

            reviewRepository.delete(review.get());
            return CheckReviewResponse.of(true);
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


    /**
     * 사장님 답변  답변다는 사장님의 토큰 + dto를 받는다.
     * 1. 빌드로 리뷰(맴버 아이디 받아옴 dto)량 연결해서
     * 2. 글생성
     */
    @Transactional
    public String createAnswer(String email, AnswerRequest answerRequest) {

        // TODO: 2021-05-17 join 쿼리 단축시키기
        Optional<Member> member = memberRepository.findByEmail(email);
        Optional<Store> store = storeRepository.findByMemberId(member.get().getId());

        Answer answer = Answer.builder().
                comment(answerRequest.getComment()).
                dateTime(DateTime.now().toString()).
                memberId(answerRequest.getMemberId()).
                storeName(store.get().getName()).
                storeId(store.get().getId()).
                build();

        answerRepository.save(answer);
        return "답변 작성이 완료되었습니다.";
    }


}
