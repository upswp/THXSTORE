package com.ssafy.thxstore.member.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ModifyPutMemberRequest {

    @NotNull
    private Long id;

    @NotEmpty
    private String address;

    @NotNull
    private Double let;

    @NotNull
    private Double lon;
}
