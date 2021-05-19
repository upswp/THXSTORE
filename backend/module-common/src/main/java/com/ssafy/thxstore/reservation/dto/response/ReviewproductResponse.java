package com.ssafy.thxstore.reservation.dto.response;

import com.ssafy.thxstore.reservation.dto.ReservationGroupDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewproductResponse {
    private String productName;

    @Builder
    public ReviewproductResponse( String productName) {
        this.productName = productName;

    }
}
