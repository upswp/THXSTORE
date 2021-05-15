package com.ssafy.thxstore.member.dto.response;

import com.ssafy.thxstore.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPutMemberResponse {
    private Long id;
    private Double lat;
    private Double lon;

    public static ModifyPutMemberResponse of(Member member) {
        return new ModifyPutMemberResponse(member.getId(), member.getLat(), member.getLon());
    }
}
