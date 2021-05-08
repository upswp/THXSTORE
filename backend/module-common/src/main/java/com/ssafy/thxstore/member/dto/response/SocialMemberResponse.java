package com.ssafy.thxstore.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialMemberResponse {
    private Boolean check;

    public static SocialMemberResponse of(Boolean check){
        return new SocialMemberResponse(check);
    }
}
