package com.ssafy.thxstore.reservation.dto.request;

import com.ssafy.thxstore.reservation.domain.ReservationStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderRequest {
    @NotBlank
    private Long userId;

    @NotBlank
    private Long cartId;

    private ReservationStatus reservationStatus;

}
