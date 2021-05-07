package com.ssafy.thxstore.member.dto.response;

import com.ssafy.thxstore.member.domain.Member;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialMemberResponse {
    private Long id;
    private String email;
    private String profileImage;

    public static SocialMemberResponse of(Member socialMember){
        return new SocialMemberResponse(socialMember.getId(),socialMember.getEmail(),socialMember.getProfileImage());
    }
}
