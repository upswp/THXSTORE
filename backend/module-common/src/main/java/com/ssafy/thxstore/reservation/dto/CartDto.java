package com.ssafy.thxstore.reservation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartDto {
    @NotBlank
    private Long userId;

    @NotBlank
    private Long productId;

    @NotBlank
    private String productname;

    @NotBlank
    private int price;  //편의를 위해

    @NotBlank
    private int count;
}
