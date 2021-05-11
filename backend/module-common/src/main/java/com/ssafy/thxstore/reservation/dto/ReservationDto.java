package com.ssafy.thxstore.reservation.dto;

import com.ssafy.thxstore.reservation.domain.ReservationStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationDto {
//    @NotBlank
    private Long userId;

//    @NotBlank
    private String email;

//    @NotBlank
    private Long storeId;

//    @NotBlank
    private ReservationStatus reservationStatus;

//    @NotBlank
    private List<ReservationGroupDto> reservationGroups;

    @Builder
    public ReservationDto(String email,Long storeId, Long userId, List<ReservationGroupDto> reservationGroups, ReservationStatus reservationStatus) {
        this.userId = userId;
        this.storeId = storeId;
        this.reservationGroups = reservationGroups;
        this.reservationStatus = reservationStatus;
        this.email = email;
    }
}