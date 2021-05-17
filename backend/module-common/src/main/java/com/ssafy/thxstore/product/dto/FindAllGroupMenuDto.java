package com.ssafy.thxstore.product.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FindAllGroupMenuDto {
    Long productId;
    String name;
    Integer price;
    String productImg;
    String amount;
    Integer rate;
    Integer stock;
    String introduce;
}
