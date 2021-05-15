package com.ssafy.thxstore.product.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeDealProductResponse {
    Long productId;
    String name;
    Integer price;
    String productImg;
    String amount;
    String introduce;
    Integer rate;
    Integer stock;
}
