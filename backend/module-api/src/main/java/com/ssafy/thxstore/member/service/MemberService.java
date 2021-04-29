package com.ssafy.thxstore.member.service;

import com.ssafy.thxstore.common.exceptions.AuthException;
import com.ssafy.thxstore.common.exceptions.ErrorCode;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.dto.SignUpRequest;
import com.ssafy.thxstore.member.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class MemberService implements UserDetailsService {

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    public Member registerMember(SignUpRequest signUpRequest){
        if(memberRepository.existsByEmail(signUpRequest.getEmail())){
            throw new AuthException(ErrorCode.DUPLICATED_EMAIL);
        }
        if(memberRepository.existsByNickName(signUpRequest.getNickName())){
            throw new AuthException(ErrorCode.DUPLICATED_NICKNAME);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
        Member member = modelMapper.map(signUpRequest,Member.class);
        memberRepository.save(member);
        return member;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(username));
        return new User(member.getEmail(), member.getPassword(), member.getAuthorities());
    }
}
