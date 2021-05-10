package com.ssafy.thxstore.store.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateStoreFileDto {
    private String name;

    private String mainAddress;

    private String subAddress;

    private String phoneNum;

    private Double lat;

    private Double lon;

    private String license;

    private MultipartFile licenseImg;
}
