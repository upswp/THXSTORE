package com.ssafy.thxstore.member.service;

import com.ssafy.thxstore.common.exceptions.AuthException;
import com.ssafy.thxstore.common.exceptions.ErrorCode;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.domain.Social;
import com.ssafy.thxstore.member.dto.MemberDto;
import com.ssafy.thxstore.member.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class MemberService {

    private final MemberRepository memberRepository;

    public Long registerMember(MemberDto memberDto){
        if(memberRepository.existsByEmail(memberDto.getEmail())){
            throw new AuthException(ErrorCode.DUPLICATED_EMAIL);
        }
        if(memberRepository.existsByNickName(memberDto.getNickName())){
            throw new AuthException(ErrorCode.DUPLICATED_NICKNAME);
        }
        Member member = new Member();
        member.setEmail(memberDto.getEmail());
        member.setPassword(memberDto.getPassword());
        member.setNickName(memberDto.getNickName());

        Member result = memberRepository.save(member);
        return result.getId();
    }



}
