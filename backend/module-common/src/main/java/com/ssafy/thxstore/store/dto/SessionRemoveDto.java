package com.ssafy.thxstore.store.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionRemoveDto {
    String storeId;
    String token;
}
