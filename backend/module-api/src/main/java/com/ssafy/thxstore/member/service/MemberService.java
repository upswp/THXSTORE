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
        if(memberRepository.existsByNickName(signUpRequest.getNickName())){
            throw new AuthException(ErrorCode.DUPLICATED_NICKNAME);
        }
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));

        Member member ;
        if(signUpRequest.getSocial() == null && signUpRequest.getUserId() == null&& signUpRequest.getImage() == null){
            member = Member.builder()
                    .email(signUpRequest.getEmail())
                    .password(signUpRequest.getPassword())
                    .nickName(signUpRequest.getNickName())
                    .role(MemberRole.USER)
                    .social(Social.LOCAL)
                    .userId(null)
                    .image(null)
                    .build();
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            memberRepository.save(member);
        }else {
            member = Member.builder()
                    .email(signUpRequest.getEmail())
                    .password(signUpRequest.getPassword())
                    .nickName(signUpRequest.getNickName())
                    .role(MemberRole.USER)
                    .social(signUpRequest.getSocial())
                    .image(signUpRequest.getImage())
                    .userId(signUpRequest.getUserId())
                    .build();
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            memberRepository.save(member);
        }
        return member;
    }
}
