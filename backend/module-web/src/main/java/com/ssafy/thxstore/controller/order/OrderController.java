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

/**
 * 장바구니 생성
 */
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

    @PostMapping("/cart/order")
    public ResponseEntity<String> addOrder(@RequestBody OrderRequest orderRequest){

        reservationService.addOrder(orderRequest);

        return new ResponseEntity<>("생성완료", HttpStatus.OK);
//        return ResponseEntity.created(li.getUri()).body(li.getOrderResource());
    }

    /**
     * 주문 조회
     */

    /**
     * 결제 정보 조회
     */

    /**
     * 주문 상태 조회
     */



}
