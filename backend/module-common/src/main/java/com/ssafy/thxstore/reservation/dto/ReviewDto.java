package com.ssafy.thxstore.reservation.dto;

import com.ssafy.thxstore.reservation.domain.ReservationStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDto {
    @NotBlank
    private String comment;

    private String logo;

    @NotBlank
    private Long storeId;

    @NotBlank
    private String storeName;

    @NotBlank
    private Long memberId;

    @NotBlank
    private Long reservationId;

    @NotBlank
    private int star;

    @NotBlank
    private String dateTime;

    @Builder
    public ReviewDto(String logo,String comment, int star, String dateTime,Long storeId,Long memberId,Long reservationId,String storeName) {
        this.comment = comment;
        this.logo = logo;
        this.star =star;
        this.dateTime = dateTime;
        this.storeId = storeId;
        this.memberId =memberId;
        this.reservationId = reservationId;
        this.storeName = storeName;
    }
}
