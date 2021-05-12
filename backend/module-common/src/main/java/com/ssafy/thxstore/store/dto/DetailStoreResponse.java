package com.ssafy.thxstore.store.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DetailStoreResponse {
    private SideInfo sideInfo;
    private BaseInfo baseInfo;
}
