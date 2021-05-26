package com.ssafy.thxstore.product.dto;


import lombok.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDto {
    private String productName;
}
