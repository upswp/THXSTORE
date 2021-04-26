package com.ssafy.thxstore.member.service;

import com.ssafy.thxstore.common.exceptions.AuthException;
import com.ssafy.thxstore.common.exceptions.ErrorCode;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.dto.MemberDto;
import com.ssafy.thxstore.member.domain.MemberRole;
import com.ssafy.thxstore.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Component
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    public Member registerMember(MemberDto memberDto) {
        if(memberRepository.existsByEmail(memberDto.getEmail())){
            throw new AuthException(ErrorCode.DUPLICATED_EMAIL);
        }
        if (memberRepository.existsByNickName(memberDto.getNickName())){
            throw new AuthException(ErrorCode.DUPLICATED_NICKNAME);
        }
        //TODO 권한부여 관련 추가 작업 진행하기.
        Member member = modelMapper.map(memberDto,Member.class);
        Member result = memberRepository.save(member);

        return result;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(username));
        return new User(member.getEmail(), member.getPassword(), authorities(member.getRoles()));
    }


    private Collection<? extends GrantedAuthority> authorities(Set<MemberRole> roles) {
        return roles
                .stream()
                .map(r-> new SimpleGrantedAuthority("ROLE" + r.name()))
                .collect(Collectors.toSet());
    }


}
