package com.ssafy.thxstore.member.service;

import com.ssafy.thxstore.common.exceptions.AuthException;
import com.ssafy.thxstore.common.exceptions.ErrorCode;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.domain.MemberRole;
import com.ssafy.thxstore.member.dto.SignUpRequest;
import com.ssafy.thxstore.member.dto.SignUpResponse;
import com.ssafy.thxstore.member.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class MemberService {

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    public Member registerMember(SignUpRequest signUpRequest){
        if(memberRepository.existsByEmail(signUpRequest.getEmail())){
            throw new AuthException(ErrorCode.DUPLICATED_EMAIL);
        }
        if(memberRepository.existsByNickName(signUpRequest.getNickName())){
            throw new AuthException(ErrorCode.DUPLICATED_NICKNAME);
        }
        Member member = modelMapper.map(signUpRequest,Member.class);
        memberRepository.save(member);
        return member;
    }



}
