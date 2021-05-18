package com.ssafy.thxstore.reservation.dto.request;

import com.ssafy.thxstore.reservation.domain.ReservationStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatusRequest {
    @NotBlank
    private ReservationStatus reservationStatus;

    @NotBlank
    private Long memberId;

    @NotBlank
    private Long storeId;

//    @NotBlank
//    private Long memberId;
}