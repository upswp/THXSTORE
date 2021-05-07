package com.ssafy.thxstore.store.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreChangedDto {
    Long storeId;
    String storeCategory;
    String openTime;
    String closeTime;
    String closeDay;
    String introduce;
    MultipartFile thumbnailImg;
    MultipartFile profileImg;
}
