package com.ssafy.thxstore.controller.order;

import com.ssafy.thxstore.reservation.domain.ReservationGroup;
import com.ssafy.thxstore.reservation.domain.ReservationStatus;
import com.ssafy.thxstore.reservation.dto.ReservationDto;
import com.ssafy.thxstore.reservation.dto.ReservationGroupDto;
import com.ssafy.thxstore.reservation.dto.StatusRequest;
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
 *
 *
 * ]
 */
@PostMapping("/reservation")
public ResponseEntity<String> addReservation(@Valid @RequestBody ReservationDto reservation){

    System.out.println("받아온거 확인");
    System.out.println("유저아이디: " + reservation.getUserId());
    System.out.println("주문 그룹: " + reservation.getReservationGroups());
    reservationService.addReservation(reservation);

    return new ResponseEntity<>("생성완료", HttpStatus.OK);
}

    /**
     * 주문 조회
     * 1. reservation_group 테이블에서 member_id 로 찾아서 List<ReservationGroup> 형식으로 리턴
     */

    @GetMapping("/reservation/{memberId}")
    public ResponseEntity getReservation(@PathVariable Long memberId){

        List<ReservationGroupDto> li = reservationService.getReservation(memberId);

        return new ResponseEntity<>(li, HttpStatus.OK);
//        return ResponseEntity.created(li.getUri()).body(li.getOrderResource());
    }

//    /**
//     *  사장님 or 사용자의 주문 취소 버튼 클릭 후 후 -> 테이블 자체에서 삭제
//     */
//
//    @DeleteMapping("/reservation/{memberId}/{storeId}")
//    public ResponseEntity deleteReservation(@PathVariable Long memberId,@PathVariable Long storeId){
//
//        reservationService.deleteReservation(memberId,storeId);
//
//        return new ResponseEntity<>("주문 취소 되었습니다.", HttpStatus.OK);
////        return ResponseEntity.created(li.getUri()).body(li.getOrderResource());
//    }


//    /**
//     * 1. 주문 테이블에 들어간 상황 사장님이 수령 확인 버튼 누르면 주문 status 변경 memberId(321) 님 이시죠? 물건 주고 버튼 누르면 주문 테이블에서 상태 변화
//     * 2. DEFAULT -> ACCEPT 주문 승락
//     * 3. ACCEPT -> STAND_BY 상품(음식) 완료 후 수령 대기
//     * 4. STAND_BY -> FINISH 수령 완료
//     */
//
//    @PutMapping("/reservation/{memberId}") // v2 mem id로 받아서 검색 후 수정, 받아오는 형식 memformdto
//    public ResponseEntity<String> updateReservation(@PathVariable Long memberId , @RequestBody StatusRequest status) {
//
//        reservationService.statusUpdate(memberId, status);
//
//        return new ResponseEntity<>("주문 상태를 변경했습니다.", HttpStatus.OK);
//    }//맴버정보보기를 눌러서 확인

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
