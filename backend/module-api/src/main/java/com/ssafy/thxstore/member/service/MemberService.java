package com.ssafy.thxstore.member.service;

import com.ssafy.thxstore.common.exceptions.AuthException;
import com.ssafy.thxstore.common.exceptions.ErrorCode;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.domain.MemberRole;
import com.ssafy.thxstore.member.domain.Social;
import com.ssafy.thxstore.member.dto.SignUpRequest;
import com.ssafy.thxstore.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
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
}
