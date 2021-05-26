package com.ssafy.thxstore.product.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeDealProductInfoResponse {
    String status;
    String startTime;
    List<TimeDealProductResponse> timeDeal;
}