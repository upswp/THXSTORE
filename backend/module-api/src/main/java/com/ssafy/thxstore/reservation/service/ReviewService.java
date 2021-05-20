package com.ssafy.thxstore.reservation.service;

import com.ssafy.thxstore.common.exceptions.AuthException;
import com.ssafy.thxstore.common.exceptions.ErrorCode;
import com.ssafy.thxstore.member.repository.MemberRepository;
import com.ssafy.thxstore.reservation.domain.Answer;
import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.domain.Review;
import com.ssafy.thxstore.reservation.dto.AnswerDto;
import com.ssafy.thxstore.reservation.dto.ReviewDto;
import com.ssafy.thxstore.reservation.dto.response.CheckReviewResponse;
import com.ssafy.thxstore.reservation.dto.response.ReviewproductResponse;
import com.ssafy.thxstore.reservation.repository.AnswerRepository;
import com.ssafy.thxstore.reservation.repository.ReservationRepository;
import com.ssafy.thxstore.reservation.repository.ReviewRepository;
import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.repository.StoreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.util.*;

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

    public Review createReview(ReviewDto reviewDto) throws Exception{
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT);
        String time = timeFormat.format(new Date());

        Optional<Reservation> reservation = reservationRepository.findById(reviewDto.getReservationId());
        Optional<Store> store = storeRepository.findById(reviewDto.getStoreId());


        Optional<Review> newreview =reviewRepository.findByReservationId(reviewDto.getReservationId());
        if(newreview.isPresent()){
            throw new AuthException(ErrorCode.CHECK_REVIEW);
        }

        Review review = Review.builder().
                memberName(reviewDto.getMemberName()).
                dateTime(DateTime.now().toString()).
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
//        List<ReviewproductResponse> ReservationGroupDtoList = new LinkedList<>();

        if(type == "member") {

            //reservation id 찾아오려면 쿼리 하나 날려야됨 일단 null 보내고 필요하면 넣자

            ReviewList = reviewRepository.findReviewByMemberId(Id);
            for(int i =0 ;i<ReviewList.size(); i++){ //1개
                List<ReviewproductResponse> ReservationGroupDtoList = new LinkedList<>();
                //Review 랑 스토어 매핑하면 쿼리 줄일 수 있겠다
                Optional<Store> store= storeRepository.findById(ReviewList.get(i).getStoreId());

                for(int j =0;ReviewList.get(i).getReservation().getReservationGroup().size()>j;j++) {
                    ReviewproductResponse reservationGroupDto = ReviewproductResponse.builder().
                            productName(ReviewList.get(i).getReservation().getReservationGroup().get(j).getProductName()).
                            build();
                    ReservationGroupDtoList.add(reservationGroupDto);
                }

                AnswerDto answerDto = new AnswerDto();
                Optional<Answer> answer = answerRepository.findByReviewId(ReviewList.get(i).getId());  //리뷰아이디를 통해 하나 찾아와~~

                if(answer.isPresent()) {
                    answerDto = AnswerDto.builder().
                            comment(answer.get().getComment()).
                            dateTime(answer.get().getDateTime()).
                            storeId(answer.get().getStoreId()).
                            reveiwId(answer.get().getReviewId()).
                            build();
                }

                ReviewDto reviewDto = ReviewDto.builder().
                        answerDto(answerDto).
                        memberName(ReviewList.get(i).getMemberName()).
                        reservationGroupDtoList(ReservationGroupDtoList).
                        reviewId(ReviewList.get(i).getId()).
                        logo(store.get().getLogo()).
                        reservationId(ReviewList.get(i).getReservation().getId()).
                        storeId(ReviewList.get(i).getStoreId()).
                        memberId(ReviewList.get(i).getMemberId()).
                        storeName(ReviewList.get(i).getStoreName()).
                        star(ReviewList.get(i).getStar()).
                        dateTime(ReviewList.get(i).getDateTime()).
                        comment(ReviewList.get(i).getComment()).
                        build();

                ReviewDtoList.add(reviewDto);
                ReviewDtoList.sort(Comparator.reverseOrder());
            }
        }else{
            ReviewList = reviewRepository.findReviewByStoreId(Id);
            for(int i =0 ;i<ReviewList.size(); i++){

                List<ReviewproductResponse> ReservationGroupDtoList = new LinkedList<>();

                for(int j =0;ReviewList.get(i).getReservation().getReservationGroup().size()>j;j++) {
                    ReviewproductResponse reservationGroupDto = ReviewproductResponse.builder().
                            productName(ReviewList.get(i).getReservation().getReservationGroup().get(j).getProductName()).
                            build();
                    ReservationGroupDtoList.add(reservationGroupDto);
                }
                //리뷰 아이디를 통해 엔서에서 조회하자
                    AnswerDto answerDto = new AnswerDto();
                    Optional<Answer> answer = answerRepository.findByReviewId(ReviewList.get(i).getId());

                    if(answer.isPresent()) {
                        answerDto = AnswerDto.builder().
                                comment(answer.get().getComment()).
                                dateTime(answer.get().getDateTime()).
                                storeId(answer.get().getStoreId()).
                                reveiwId(answer.get().getReviewId()).
                                build();
                    }
                ReviewDto reviewDto = ReviewDto.builder().
                        answerDto(answerDto).
                        reservationGroupDtoList(ReservationGroupDtoList).
                        reservationId(ReviewList.get(i).getReservation().getId()).
                        memberName(ReviewList.get(i).getMemberName()).
                        reviewId(ReviewList.get(i).getId()).
                        profileImg(ReviewList.get(i).getReservation().getMember().getProfileImage()).
                        storeId(ReviewList.get(i).getStoreId()).
                        memberId(ReviewList.get(i).getMemberId()).
                        storeName(ReviewList.get(i).getStoreName()).
                        star(ReviewList.get(i).getStar()).
                        dateTime(ReviewList.get(i).getDateTime()).
                        comment(ReviewList.get(i).getComment()).
                        build();

                ReviewDtoList.add(reviewDto);
                ReviewDtoList.sort(Comparator.reverseOrder());

            }
        }

        return ReviewDtoList;
    }


    /**
     * 사장님 답변  답변다는 사장님의 토큰 + dto를 받는다.
     * 1. 빌드로 리뷰(맴버 아이디 받아옴 dto)량 연결해서
     * 2. 글생성
     */
    @Transactional
    public String createAnswer(AnswerDto answerDto) {

        // TODO: 2021-05-17 join 쿼리 단축시키기
//        Optional<Member> member = memberRepository.findById(answerRequest.getMemberId());
//        Optional<Store> store = storeRepository.findByMemberId(member.get().getId());

        Optional<Review> review = reviewRepository.findById(answerDto.getReviewId());

        //리뷰도 찾아서 연결해주자
        Optional<Answer> newAnswer =answerRepository.findByReviewId(answerDto.getReviewId());
        if(newAnswer.isPresent()){
            throw new AuthException(ErrorCode.CHECK_ANSWER);
        }

        Answer answer = Answer.builder().
                reviewId(review.get().getId()).
                comment(answerDto.getComment()).
                dateTime(DateTime.now().toString()).
                storeId(review.get().getStoreId()).
                build();

        answerRepository.save(answer);
        review.get().getNewAnswer(answer);
        return "답변 작성이 완료되었습니다.";
    }


}
