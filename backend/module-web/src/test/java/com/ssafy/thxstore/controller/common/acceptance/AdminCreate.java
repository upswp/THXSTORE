package com.ssafy.thxstore.controller.common.acceptance;

import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.domain.MemberRole;
import com.ssafy.thxstore.member.domain.Social;
import com.ssafy.thxstore.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Profile("test")
@RequiredArgsConstructor
public class AdminCreate {

    public static final String ADMIN_NAME = "THXSTORE_ADMIN";
    public static final String ADMIN_EMAIL = "thxstore.help@gmail.com";
    public static final String ADMIN_PASSWORD = "Password123!";

    private MemberRepository memberRepository;

    private PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(){
        Member member = Member.builder()
                .email(ADMIN_EMAIL)
                .password(ADMIN_PASSWORD)
                .nickName(ADMIN_NAME)
                .roles(MemberRole.ADMIN)
                .social(Social.LOCAL)
                .build();
        passwordEncoder.encode(member.getPassword());
        memberRepository.save(member);
    }
}
