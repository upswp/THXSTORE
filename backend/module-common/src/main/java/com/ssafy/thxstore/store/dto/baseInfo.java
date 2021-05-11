package com.ssafy.thxstore.store.dto;

import com.ssafy.thxstore.member.domain.MemberRole;
import com.ssafy.thxstore.store.domain.CheckStore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class baseInfo {
    Long storeId;
    String name;
    String mainAddress;
    String subAddress;
    String phoneNum;
    Double lat;
    Double lon;
    String license;
    String licenseImg;
    CheckStore checkStore;
    MemberRole role;
    Boolean timeDealCheck;
}
