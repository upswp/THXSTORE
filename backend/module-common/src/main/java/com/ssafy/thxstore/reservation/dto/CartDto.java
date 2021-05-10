package com.ssafy.thxstore.reservation.dto;

import lombok.AccessLevel;
import lombok.Builder;
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

    @NotBlank
    private Long storeId;

    @Builder
    public CartDto(Long storeId,Long userId, Long productId,String productname, int price, int count) {
        this.userId = userId;
        this.storeId = storeId;
        this.productId = productId;
        this.productname =productname;
        this.price = price;
        this.count = count;
    }
}