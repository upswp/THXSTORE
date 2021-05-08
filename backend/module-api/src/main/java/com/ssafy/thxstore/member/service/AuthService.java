package com.ssafy.thxstore.member.service;

import com.ssafy.thxstore.common.exceptions.AuthException;
import com.ssafy.thxstore.common.exceptions.ErrorCode;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.domain.MemberRole;
import com.ssafy.thxstore.member.domain.Social;
import com.ssafy.thxstore.member.dto.request.CheckEmailRequest;
import com.ssafy.thxstore.member.dto.request.SignUpRequest;
import com.ssafy.thxstore.member.dto.request.SocialMemberRequest;
import com.ssafy.thxstore.member.dto.response.CheckEmailResponse;
import com.ssafy.thxstore.member.dto.response.SocialMemberResponse;
import com.ssafy.thxstore.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입진행
     * @param signUpRequest 회원가입 RequestDto
     * @return Member
     */
    public Member registerMember(SignUpRequest signUpRequest){
        if(memberRepository.existsByEmail(signUpRequest.getEmail())){
            throw new AuthException(ErrorCode.DUPLICATED_EMAIL);
        }
        Member member ;
        if(signUpRequest.getSocial() == null && signUpRequest.getUserId() == null&& signUpRequest.getProfileImage() == null){
            member = Member.builder()
                    .email(signUpRequest.getEmail())
                    .password(signUpRequest.getPassword())
                    .nickname(signUpRequest.getNickname())
                    .role(MemberRole.USER)
                    .social(Social.LOCAL)
                    .userId(null)
                    .profileImage(null)
                    .build();
        }else {
            member = Member.builder()
                    .email(signUpRequest.getEmail())
                    .password(signUpRequest.getPassword())
                    .nickname(signUpRequest.getNickname())
                    .role(MemberRole.USER)
                    .social(signUpRequest.getSocial())
                    .profileImage(signUpRequest.getProfileImage())
                    .userId(signUpRequest.getUserId())
                    .build();
        }
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
        return member;
    }

    /**
     * 소셜 로그인시 socialMemberRequest 들어온 UserId와 Social값으로 Member 찾기.
     * @param socialMemberRequest UserId & Social use
     * @return Member
     */
    public SocialMemberResponse findSocialMember(SocialMemberRequest socialMemberRequest){
        Member socialMember = memberRepository
                .findByUserIdAndSocial(socialMemberRequest.getUserId(), socialMemberRequest.getSocial().toString())
                .orElseThrow(() -> new AuthException(ErrorCode.UNAUTHORIZED_MEMBER));
        return SocialMemberResponse.of(socialMember);
    }

    public CheckEmailResponse existsByEmail(CheckEmailRequest checkEmailRequest){
        Boolean check = memberRepository.existsByEmail(checkEmailRequest.getEmail());
        return CheckEmailResponse.of(check);
    }
}
