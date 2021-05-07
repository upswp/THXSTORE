package com.ssafy.thxstore.store.dto;

import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.store.domain.CheckStore;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateStoreDto {
    private Member member;

    private Long memberId;

    private String name;

    private String mainAddress;

    private String subAddress;

    private String phoneNum;

    private String license;

    private String licenseImg;

    private CheckStore checkStore;
}
