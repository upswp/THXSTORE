package com.ssafy.thxstore.store.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreUnchangedDto {
    Long storeId;
    String name;
    String mainAddress;
    String subAddress;
    String phoneNum;
    Double lat;
    Double lon;
    String license;
    MultipartFile licenseImg;
}
//store_id, name, main_address, sub_address, phone_num, license, licesne_img