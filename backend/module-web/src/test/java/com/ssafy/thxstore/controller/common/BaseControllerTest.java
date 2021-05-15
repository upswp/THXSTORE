package com.ssafy.thxstore.controller.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.domain.MemberRole;
import com.ssafy.thxstore.member.repository.MemberRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.ssafy.thxstore.member.domain.Social.KAKAO;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@Ignore
public class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ModelMapper modelMapper;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected MemberRepository memberRepository;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    /**
     * 최초 유저를 생성한다.
     * @param email 생성할 유저의 이메일 (unique)
     * @param userId 생성할 유저의 유저 아이디 (unique)
     */
    protected void generateMember(String email, String userId) {
        Member member = Member.builder()
                .id(1L)
                .email(email)
                .nickname("evan")
                .password("password123")
                .profileImage("default image url")
                .social(KAKAO)
                .userId(userId)
                .lat(37.33)
                .lon(126.59)
                .phoneNumber("010-123-1234")
                .role(MemberRole.ROLE_USER)
                .build();

        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }
}
