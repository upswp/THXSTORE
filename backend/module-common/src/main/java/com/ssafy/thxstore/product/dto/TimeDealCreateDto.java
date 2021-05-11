package com.ssafy.thxstore.product.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeDealCreateDto {
    Long storeId;
    String startTime;
    List<TimeDealProductDto> timeDealProductDtos;
}
