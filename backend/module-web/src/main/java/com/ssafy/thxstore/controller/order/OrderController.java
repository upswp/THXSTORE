package com.ssafy.thxstore.controller.order;

import com.ssafy.thxstore.controller.config.AppProperties;
import com.ssafy.thxstore.controller.member.AuthController;
import com.ssafy.thxstore.controller.member.Resource.CheckEmailResource;
import com.ssafy.thxstore.controller.order.Resource.CheckReviewResource;
import com.ssafy.thxstore.controller.order.Resource.ReviewResource;
import com.ssafy.thxstore.reservation.domain.Review;
import com.ssafy.thxstore.reservation.dto.*;
import com.ssafy.thxstore.reservation.dto.request.AnswerRequest;
import com.ssafy.thxstore.reservation.dto.request.StatusRequest;
import com.ssafy.thxstore.reservation.dto.response.CheckReviewResponse;
import com.ssafy.thxstore.reservation.service.ReservationService;
import com.ssafy.thxstore.reservation.service.ReviewService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping(value = "/order", produces = MediaTypes.HAL_JSON_VALUE)
public class OrderController {
private final ReservationService reservationService;
private final ReviewService reviewService;
private final AppProperties appProperties;
/**
 * 주문 생성
 * 스토어의 매뉴 창에서 메뉴 선택 후 바로 주문 한다.  STAND_BY
 * 주문 테이블에 넣어줌
 * reservationStatus   --- >   STAND_BY   -> 사장님 승인 후 ->  ACCEPT   -> 사장님 수령 확인 완료 후  --> FINISH
 * [
     * {
     *
         * reser_id :12,(pk)
         * user_id :1,
         * store_id :3,
         * reservationStatus : stand_by
             * [{불닭 ,},{},{}] -> 그룹 엔티티 하나 만들고 연결하자   --  list  -- dto로 전할때는 build로 넣어줘
     *
     * },
 * ]
 *
 *
 */

//주문등록 주문이 들어왔을 때 ---->  재고 확인 후   stock 다떨어졌으면 품절된 상품이 있습니다 return
@PostMapping("/reservation")
public ResponseEntity<String> addReservation(@RequestHeader String authorization, @RequestBody ReservationDto reservation){

    String email = jwtToEmail(authorization);
    String result = reservationService.addReservation(email,reservation);

    return new ResponseEntity<>(result, HttpStatus.OK);
}

    /**
     * 주문 조회
     * 1. reservation_group 테이블에서 member_id 로 찾아서 List<ReservationGroup> 형식으로 리턴
     *
     */

    //토큰 id 포함한 객체로 받았으면 더 좋았을듯
    @GetMapping("/reservation/member")
    public ResponseEntity getReservation(@RequestHeader String authorization){

        String email = jwtToEmail(authorization);
        List<ReservationDto> li = reservationService.getReservation(email,"member");

        return new ResponseEntity<>(li, HttpStatus.OK);
//        return ResponseEntity.created(li.getUri()).body(li.getOrderResource());
    }

//    /**
//     * 사장님 입장 주문 갱신
//     * ->pusher 이벤트 들어오면 한번 호출 -> 새롭게 들어온 주문 목록을 리턴한다.
//     */
//
//    @GetMapping("/reservation/store/{storeId}")
//    public ResponseEntity reflashReservation(@PathVariable Long storeId){
//
//        List<ReservationDto> li = reservationService.getReservation(storeId,"store");
//        return new ResponseEntity<>(li, HttpStatus.OK);
////        return ResponseEntity.created(li.getUri()).body(li.getOrderResource());
//    }

    /**
     *  사장님 or 사용자의 주문 취소 버튼 클릭 후 후 -> 테이블 자체에서 삭제
     *  member_id와 store_id(사장님의 email 토큰) 로 reservation 테이블에서 삭제한다.
     *  사장님 email 토큰은 로그인 한 사장님만 알 수 있음 ->
     */

    /**
     * 회원의 주문 취소  authorization -> 회원의 email 토큰 -> 이걸로 member id 가져온다
     */
    @DeleteMapping("/reservation/member")
    public ResponseEntity<String> deleteReservationForMember(@RequestHeader String authorization,@RequestParam("storeId") Long storeId){
        String email = jwtToEmail(authorization);
        String result = reservationService.deleteReservation(email,storeId,"member");

        return new ResponseEntity<>(result, HttpStatus.OK);
//        return ResponseEntity.created(li.getUri()).body(li.getOrderResource());
    }

    /**
     * 사장님의 주문 취소
     */
    @DeleteMapping("/reservation/store")
    public ResponseEntity deleteReservationForStore(@RequestHeader String authorization,@RequestParam("memberId") Long memberId){
        String email = jwtToEmail(authorization);
        reservationService.deleteReservation(email,memberId,"store");

        return new ResponseEntity<>("주문 취소 되었습니다.", HttpStatus.OK);
//        return ResponseEntity.created(li.getUri()).body(li.getOrderResource());
    }

    /**
     * 1. 주문 테이블에 들어간 상황 사장님이 수령 확인 버튼 누르면 주문 status 변경 memberId(321) 님 이시죠? 물건 주고 버튼 누르면 주문 테이블에서 상태 변화
     *
     * 2. DEFAULT -> ACCEPT 주문 승락 버튼
     * 3. ACCEPT -> STAND_BY 상품(음식) 조리 완료 후 수령 대기 버튼
     * 4. STAND_BY -> FINISH 수령 완료 버튼
     */


    /**
     * 사장님 -> 토큰 ,  유저 아디이 받고 ,  스토어 아이디 받아서   -스토어테이블에서   스토어 아이디
     * 사장님이 상태를 변경한다.
     */

    @PutMapping("/reservation/status") // v2 mem id로 받아서 검색 후 수정, 받아오는 형식 memformdto
    public ResponseEntity<String> updateStatus(@RequestHeader String authorization,@RequestBody StatusRequest status) {
        String email = jwtToEmail(authorization);

        String result = reservationService.statusUpdate(email,status);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }//맴버정보보기를 눌러서 확인

    /**
     * 사장님 입장에서 조회 ->본인의 가게에 들어온 주문 내역만
     *
     * 사장님이 조회 페이지 눌렀을 경우 푸셔 인스턴스 만들고
     */

    @GetMapping("/reservation/store")
    public ResponseEntity getStoreReservation(@RequestHeader String authorization){
        String email = jwtToEmail(authorization);
        List<ReservationDto> li = reservationService.getReservation(email,"store");
        return new ResponseEntity<>(li, HttpStatus.OK);
//        return ResponseEntity.created(li.getUri()).body(li.getOrderResource());
    }

    /**
     * 리뷰 생성 삭제 수정
     * 같은 맴버가 하나의 스토어에 또 리뷰 작성하려고 하면 "이미 작성한 해당 스토어에 작성한 리뷰가 있습니다."
     * 리뷰 작성이 가능할 때 메시지도 같이
     */
    @PostMapping("/reservation/review")
    public ResponseEntity createReview(@ModelAttribute ReviewDto reviewDto) throws Exception {
        Review newReview = reviewService.createReview(reviewDto);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(OrderController.class).slash("reservation/review").slash(newReview.getId());
        URI createUri = selfLinkBuilder.toUri();

        ReviewResource reviewResource = new ReviewResource(newReview);
        reviewResource.add(Link.of("/api/docs/index.html#resources-create-review").withRel("profile"));

        return ResponseEntity.created(createUri).body(reviewResource);
    }// 반환하는건 이걸로

    @DeleteMapping("/reservation/review/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable Long reviewId){

        CheckReviewResponse checkReviewResponse = reviewService.deleteReview(reviewId);
        CheckReviewResource checkReviewResource= new CheckReviewResource(checkReviewResponse);
        checkReviewResource.add(linkTo(OrderController.class).withRel("delete-review"));
        checkReviewResource.add(Link.of("/api/docs/index.html#delete-review").withRel("profile"));

        return ResponseEntity.ok(checkReviewResource);
    }

    @PutMapping("/reservation/review/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId,@RequestBody ReviewDto reviewDto){

        reviewService.updateReview(reviewId, reviewDto);

        return new ResponseEntity<>("수정완료", HttpStatus.OK);
    }

    /**
     * 사장님 답변
     */

    /**
     * 리뷰 조회 -> 내 리뷰 조회, 스토어의 리뷰 조회 받는 형식 -> 사용자의아이디 글내용 별점 int
     * datetime 조회
     */

    @GetMapping("/reservation/review/{memberId}")
    public ResponseEntity getReviewBymember(@PathVariable Long memberId){

        List<ReviewDto> ReviewList = reviewService.getReview(memberId,"member");

        return new ResponseEntity<>(ReviewList, HttpStatus.OK);
//        return ResponseEntity.created(li.getUri()).body(li.getOrderResource());
    }

    @GetMapping("/reservation/review/store/{storeId}")
    public ResponseEntity getReviewByStore(@PathVariable Long storeId){

        List<ReviewDto> ReviewList = reviewService.getReview(storeId,"store");

        return new ResponseEntity<>(ReviewList, HttpStatus.OK);
//        return ResponseEntity.created(li.getUri()).body(li.getOrderResource());
    }

    public String jwtToEmail(String authorization){
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(appProperties.getAuth().getTokenSecret()))
                .parseClaimsJws(authorization).getBody().getSubject();
    }

    /**
     * 사장님 답변  답변다는 사장님의 토큰 + dto를 받는다.
     * 1. 빌드로 리뷰(맴버 아이디 받아옴 dto)량 연결해서
     * 2. 글생성
     */
    
    // TODO: 2021-05-17 리뷰 조회할 때 사장님 답변이 달려 있으면 -> 사장님 답변도 함께 리턴

    @PostMapping("/reservation/answer")
    public ResponseEntity<String> createAnswer(String authorization,@RequestBody AnswerRequest answerRequest){
        String email = jwtToEmail(authorization);

        String result = reviewService.createAnswer(email,answerRequest);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
