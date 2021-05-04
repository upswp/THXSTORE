package com.ssafy.thxstore.controller.order;

import com.ssafy.thxstore.product.dto.ProductDto;
import com.ssafy.thxstore.reservation.domain.Cart;
import com.ssafy.thxstore.reservation.dto.CartDto;
import com.ssafy.thxstore.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 장바구니 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/order", produces = MediaTypes.HAL_JSON_VALUE)
public class OrderController {


private final ReservationService reservationService;
/**
 * 장바구니 생성
 */
        @PostMapping
    public ResponseEntity<String> addCart(@Valid @RequestBody List<CartDto> cartList){

        reservationService.addCart(cartList);

        return new ResponseEntity<>("생성완료", HttpStatus.OK);
    }
}
