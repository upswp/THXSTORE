package com.ssafy.thxstore.reservation.dto;

import com.ssafy.thxstore.reservation.domain.ReservationStatus;
import com.ssafy.thxstore.reservation.dto.response.ReviewproductResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDto {
    @NotBlank
    private String comment;

    private String logo;

    private String memberName;

    private Long reviewId;


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

    private List<ReviewproductResponse> reservationGroupDtoList;

    @Builder
    public ReviewDto(String memberName,List<ReviewproductResponse> reservationGroupDtoList,Long reviewId,String logo,String comment, int star, String dateTime,Long storeId,Long memberId,Long reservationId,String storeName) {
        this.comment = comment;
        this.memberName =memberName;
        this.reservationGroupDtoList =reservationGroupDtoList;
        this.reviewId = reviewId;
        this.logo = logo;
        this.star =star;
        this.dateTime = dateTime;
        this.storeId = storeId;
        this.memberId =memberId;
        this.reservationId = reservationId;
        this.storeName = storeName;
    }
}
