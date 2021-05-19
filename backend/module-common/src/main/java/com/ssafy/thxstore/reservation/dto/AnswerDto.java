package com.ssafy.thxstore.reservation.dto;

import com.ssafy.thxstore.reservation.dto.response.ReviewproductResponse;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {

    private String comment;

    private Long reviewId;

    private String dateTime;

    private Long storeId;


    @Builder
    public AnswerDto(Long reviewId,String comment, Long reveiwId, String dateTime, Long storeId) {
        this.comment = comment;
        this.reviewId = reveiwId;
        this.dateTime =dateTime;
        this.storeId =storeId;

    }
}
