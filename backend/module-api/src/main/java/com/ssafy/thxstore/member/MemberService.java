package com.ssafy.thxstore.member;

import com.ssafy.thxstore.config.MemberRole;
import com.ssafy.thxstore.domain.Member;
import com.ssafy.thxstore.domain.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public Member saveMember(SignupRequest signupRequest) {
        Member member = modelMapper.map(signupRequest, Member.class);
        Member newMember = this.memberRepository.save(member);
        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        return this.memberRepository.save(member);
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
