package com.ssafy.thxstore.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckEmailResponse {
    private Boolean check;

    public static CheckEmailResponse of(Boolean check){
        return new CheckEmailResponse(check);
    }
}
