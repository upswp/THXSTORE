package com.ssafy.thxstore.member.dto;

import com.ssafy.thxstore.member.domain.Social;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SocialMemberRequest {

    @NotBlank
    private String userId;

    @NotNull
    private Social social;
}
