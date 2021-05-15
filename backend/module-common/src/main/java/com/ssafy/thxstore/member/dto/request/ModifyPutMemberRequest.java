package com.ssafy.thxstore.member.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ModifyPutMemberRequest {

    @NotNull
    private Long id;

    @NotNull
    private Double lat;

    @NotNull
    private Double lon;
}
