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
     *
     * @param signUpRequest 회원가입 RequestDto
     * @return Member
     */
    public Member registerMember(SignUpRequest signUpRequest) {
        if (memberRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AuthException(ErrorCode.DUPLICATED_EMAIL);
        }
        Member member;
        if (signUpRequest.getSocial() == null && signUpRequest.getUserId() == null && signUpRequest.getProfileImage() == null) {
            member = Member.builder()
                    .email(signUpRequest.getEmail())
                    .password(signUpRequest.getPassword())
                    .nickname(signUpRequest.getNickname())
                    .role(MemberRole.ROLE_USER)
                    .social(Social.LOCAL)
                    .userId(null)
                    .profileImage("https://thxstore.s3.ap-northeast-2.amazonaws.com/basic_profile.jpg")
                    .lat(signUpRequest.getLat())
                    .lon(signUpRequest.getLon())
                    .address(signUpRequest.getAddress())
                    .build();
        } else {
            member = Member.builder()
                    .email(signUpRequest.getEmail())
                    .password(signUpRequest.getPassword())
                    .nickname(signUpRequest.getNickname())
                    .role(MemberRole.ROLE_USER)
                    .social(signUpRequest.getSocial())
                    .profileImage(signUpRequest.getProfileImage())
                    .userId(signUpRequest.getUserId())
                    .lat(signUpRequest.getLat())
                    .lon(signUpRequest.getLon())
                    .address(signUpRequest.getAddress())
                    .build();
        }
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
        return member;
    }

    /**
     * social,userId 값이 들어오면 유저 정보와 boolean 타입으로 확인을 한다.
     *
     * @param socialMemberRequest UserId & Social use
     * @return Member
     */
    public SocialMemberResponse findSocialMember(SocialMemberRequest socialMemberRequest) {
        Boolean check = memberRepository
                .existsByUserIdAndSocial(socialMemberRequest.getUserId(), socialMemberRequest.getSocial());
        return SocialMemberResponse.of(check);
    }

    /**
     * 이메일 중복여부 확인
     * @param checkEmailRequest
     * @return Boolean check
     */
    public CheckEmailResponse existsByEmail(CheckEmailRequest checkEmailRequest) {
        Boolean check = memberRepository.existsByEmail(checkEmailRequest.getEmail());
        return CheckEmailResponse.of(check);
    }
}
