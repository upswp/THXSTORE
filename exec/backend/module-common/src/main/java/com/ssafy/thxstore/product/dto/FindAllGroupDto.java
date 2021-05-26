package com.ssafy.thxstore.product.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FindAllGroupDto {
    Long storeId;
}
