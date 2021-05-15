package com.ssafy.thxstore.member.dto.response;

import com.ssafy.thxstore.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPatchMemberResponse {
    private Long id;

    private String profileImage;

    private String nickname;

    private String phoneNumber;

    private String password;

    public static ModifyPatchMemberResponse of(Member member) {
        return new ModifyPatchMemberResponse(member.getId(),member.getProfileImage(),member.getNickname(),member.getPhoneNumber(),member.getPassword());
    }
}
