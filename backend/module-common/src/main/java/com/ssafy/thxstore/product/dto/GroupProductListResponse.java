package com.ssafy.thxstore.product.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupProductListResponse {
    Long groupId;
    String name;
    List<FindAllGroupMenuDto> product;
}
