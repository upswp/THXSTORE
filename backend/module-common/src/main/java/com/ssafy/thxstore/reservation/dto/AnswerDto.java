package com.ssafy.thxstore.reservation.dto;

import com.ssafy.thxstore.reservation.dto.response.ReviewproductResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerDto {

    private String comment;

    private Long reveiwId;

    private String dateTime;

    private Long storeId;


    @Builder
    public AnswerDto(String comment, Long reveiwId, String dateTime, Long storeId) {
        this.comment = comment;
        this.reveiwId = reveiwId;
        this.dateTime =dateTime;
        this.storeId =storeId;

    }
}
