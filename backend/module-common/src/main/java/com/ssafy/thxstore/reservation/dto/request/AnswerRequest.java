package com.ssafy.thxstore.reservation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerRequest {

    @NotBlank
    private String comment;

    @NotBlank
    private Long memberId;

    @NotBlank
    private String dateTime;
}
