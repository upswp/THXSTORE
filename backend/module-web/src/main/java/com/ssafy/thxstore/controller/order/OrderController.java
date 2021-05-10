package com.ssafy.thxstore.controller.order;

import com.ssafy.thxstore.reservation.dto.CartDto;
import com.ssafy.thxstore.reservation.dto.OrderRequest;
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
 * 장바구니 생성
 */
@PostMapping("/cart")
public ResponseEntity<String> addCart(@Valid @RequestBody List<CartDto> cartList){

    reservationService.addCart(cartList);

    return new ResponseEntity<>("생성완료", HttpStatus.OK);
}

    /**
     * 장바구니 조회
     * 1. 사용자의 id 전달 받음 -> cart 테이블에서 user_id로 검색 후 상품 dto +수량 전달
     */

    @GetMapping("/cart/{memberId}")
    public ResponseEntity getCart(@PathVariable Long memberId){

        List<CartDto> li = reservationService.getCart(memberId);

        return new ResponseEntity<>(li, HttpStatus.OK);
//        return ResponseEntity.created(li.getUri()).body(li.getOrderResource());
    }

    /**
     * 주문 생성
     * 1. 장바구니에서 주문(예약) 버튼 눌렀을 경우
     * 2. OrderRequest -> 주문접수
     */
    // TODO: 2021-05-10 같은 cartId memberId 로 요청 예외처리
    @PostMapping()
    public ResponseEntity<String> addOrder(@RequestBody OrderRequest orderRequest){

        reservationService.addOrder(orderRequest);

        return new ResponseEntity<>("생성완료", HttpStatus.OK);
//        return ResponseEntity.created(li.getUri()).body(li.getOrderResource());
    }

    /**
     * 주문 조회 , 현제 주문내역이 전부 보임
     * memberId를 받고 주문 테이블에서 해당 아이디의 주문 정보 가져온다  cart 매핑으로 장바구니, 맴버 정보 가져올 수 있다
     * 리턴 값 -> 주문자 아이디 , 장바구니 내역(리스트)
     * 1. memId 로 조회
     */
//    @GetMapping("/{memberId}")
//    public ResponseEntity getOrder(@PathVariable Long memberId){
//
//        List<ReservationDto> li = reservationService.getOrder(memberId);
//
//        return new ResponseEntity<>(li, HttpStatus.OK);

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
