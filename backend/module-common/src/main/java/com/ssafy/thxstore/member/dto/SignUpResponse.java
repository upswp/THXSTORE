package com.ssafy.thxstore.member.dto;

import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.domain.MemberRole;
import com.ssafy.thxstore.member.domain.Social;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SignUpResponse {
    private Long id;
    private String email;
    private String password;
    private String nickName;
    private Object role;
    private Object social;

    public static SignUpResponse of(Member member){
        return new SignUpResponse(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                member.getNickName(),
                member.getRoles(MemberRole.USER),
                member.getSocial(Social.LOCAL));
    }
}
