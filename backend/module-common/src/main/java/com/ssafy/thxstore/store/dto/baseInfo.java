package com.ssafy.thxstore.store.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class baseInfo {
    Long id;
    String name;
    String mainAddress;
    String subAddress;
    String phoneNum;
    Double lat;
    Double lon;
    String license;
    String licenseImg;
}
