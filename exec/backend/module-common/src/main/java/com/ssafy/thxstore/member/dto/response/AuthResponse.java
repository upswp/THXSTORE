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
    private Long id;
    private String email;
    private String nickname;
    private Social social;
    private String profileImage;
    private String phoneNumber;
    private MemberRole role;
    private Double lat;
    private Double lon;
    private String address;

    public AuthResponse(String accessToken, Member member) {
        this.id = member.getId();
        this.accessToken = accessToken;
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.social = member.getSocial();
        this.profileImage = member.getProfileImage();
        this.phoneNumber = member.getPhoneNumber();
        this.role = member.getRole();
        this.lat = member.getLat();
        this.lon = member.getLon();
        this.address = member.getAddress();
    }

}
