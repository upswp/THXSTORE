package com.ssafy.thxstore.reservation.dto.response;

import com.ssafy.thxstore.member.dto.response.CheckEmailResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckReviewResponse {
    private Boolean check;

    public static CheckReviewResponse of(Boolean check){
        return new CheckReviewResponse(check);
    }
}
