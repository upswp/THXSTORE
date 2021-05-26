package com.ssafy.thxstore.member.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CheckEmailCodeRequest {
    @NotBlank
    private String code;
}
