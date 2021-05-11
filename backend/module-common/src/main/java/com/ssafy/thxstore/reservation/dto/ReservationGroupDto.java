package com.ssafy.thxstore.reservation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationGroupDto {

    @NotBlank
    private Long productId;

    @NotBlank
    private String productName;

    @NotBlank
    private int price;  //편의를 위해

    @NotBlank
    private int count;


}
