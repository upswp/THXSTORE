package com.ssafy.thxstore.controller.order;

import com.ssafy.thxstore.reservation.dto.ReservationDto;
import com.ssafy.thxstore.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping(value = "/order", produces = MediaTypes.HAL_JSON_VALUE)
public class OrderController {
private final ReservationService reservationService;

/**
 * 주문 생성
 * 스토어의 매뉴 창에서 메뉴 선택 후 바로 주문 한다.  STAND_BY
 * 주문 테이블에 넣어줌
 * reservationStatus   --- >   STAND_BY   -> 사장님 승인 후 ->  ACCEPT   -> 사장님 수령 확인 완료 후  --> FINISH
 */
@PostMapping("/reservation")
public ResponseEntity<String> addReservation(@Valid @RequestBody List<ReservationDto> reservationList){

    reservationService.addReservation(reservationList);

    return new ResponseEntity<>("생성완료", HttpStatus.OK);
}

    /**
     * 주문 조회
     * 1. 사용자의 id 전달 받음 -> cart 테이블에서 user_id로 검색 후 상품 dto +수량 전달
     */

//    @GetMapping("/reservation/{memberId}")
//    public ResponseEntity getReservation(@PathVariable Long memberId){
//
//        List<ReservationDto> li = reservationService.getCart(memberId);
//
//        return new ResponseEntity<>(li, HttpStatus.OK);
////        return ResponseEntity.created(li.getUri()).body(li.getOrderResource());
//    }


    /**
     * 주문 상태 조회
     */

    /**
     * 주문 테이블에 들어가게 되면, 수령 확인 주문 테이블에서 없어지는 로직
     */

    /**
     *  판매자의 주문,예약 취소 예약번호 확인 후
     */

    /**
     * 사용자의 예약 취소 (추가 기능으로)
     */

    /**
     * 판매자가 예약 번호 조회 가능하게
     */

    /**
     * 리뷰 생성 삭제 수정
     */

    /**
     * 리뷰에 대한 권한 고민
     */

    /**
     * 사장님 답변
     */

    /**
     * 리뷰 조회 -> 내 리뷰 조회, 스토어의 리뷰 조회 받는 형식 -> 사용자의아이디 글내용 별점 int
     * datetime 조회
     */
}
