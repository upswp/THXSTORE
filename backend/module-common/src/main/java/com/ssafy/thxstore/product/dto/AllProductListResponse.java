package com.ssafy.thxstore.product.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AllProductListResponse {
    private Long id;
    private String name;
    private Integer price;
    private String productImg;
    private String amount;
    private Integer rate;
    private Integer stock;
    private String introduce;
}
