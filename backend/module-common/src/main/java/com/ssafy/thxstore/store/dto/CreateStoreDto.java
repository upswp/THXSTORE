package com.ssafy.thxstore.store.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateStoreDto {

    private String name;

    private String mainAddress;

    private String subAddress;

    private String phoneNum;

    private String license;

    private String licenseImg;
}
