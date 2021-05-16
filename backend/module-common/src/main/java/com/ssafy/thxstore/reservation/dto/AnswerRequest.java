package com.ssafy.thxstore.reservation.dto;

import javax.validation.constraints.NotBlank;

public class AnswerRequest {

    @NotBlank
    private String comment;

    @NotBlank
    private Long memberId;

    @NotBlank
    private String dateTime;
}
