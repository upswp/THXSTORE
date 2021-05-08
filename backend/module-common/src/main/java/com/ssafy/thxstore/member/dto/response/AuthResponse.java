package com.ssafy.thxstore.member.dto.response;

import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.domain.MemberRole;
import com.ssafy.thxstore.member.domain.Social;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String email;
    private String nickname;
    private Social social;
    private String profileImage;
    private String location;
    private String phoneNumber;
    private MemberRole role;

    public AuthResponse(String accessToken, Member member) {
        this.accessToken = accessToken;
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.social = member.getSocial();
        this.profileImage = member.getProfileImage();
        this.location = member.getAddress();
        this.phoneNumber = member.getPhoneNumber();
        this.role = member.getRole();
    }

}
