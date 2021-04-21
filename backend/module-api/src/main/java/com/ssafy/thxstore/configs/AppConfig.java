package com.ssafy.thxstore.configs;

import com.ssafy.thxstore.config.MemberRole;
import com.ssafy.thxstore.domain.Member;
import com.ssafy.thxstore.member.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public ApplicationRunner applicationRunner() {
//        return new ApplicationRunner() {
//
//            @Autowired
//            MemberService memberService;
//
//            @Override
//            public void run(ApplicationArguments args) throws Exception {
//                Member sangwoo = Member.builder()
//                        .email("keesun@email.com")
//                        .password("keesun")
//                        .roles(Set.of(MemberRole.ADMIN, MemberRole.USER))
//                        .build();
//                memberService.saveMember(sangwoo);
//            }
//        };
//    }
}
