package com.ssafy.thxstore.reservation.dto;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class ReservationDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private List productId;

}
