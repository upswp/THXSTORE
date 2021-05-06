package com.ssafy.thxstore.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.thxstore.controller.common.BaseControllerTest;
import com.ssafy.thxstore.controller.member.docs.AuthDocumentation;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.domain.MemberRole;
import com.ssafy.thxstore.member.domain.Social;
import com.ssafy.thxstore.member.dto.request.CheckEmailRequest;
import com.ssafy.thxstore.member.dto.request.SignUpRequest;
import com.ssafy.thxstore.member.dto.request.SocialMemberRequest;
import com.ssafy.thxstore.member.dto.response.CheckEmailResponse;
import com.ssafy.thxstore.member.repository.MemberRepository;
import com.ssafy.thxstore.member.service.MemberService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends BaseControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;



    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext,
               final RestDocumentationContextProvider restDocumentationContextProvider) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();

        this.memberRepository.deleteAll();
    }

    @Test
    @DisplayName("일반회원 회원가입 진행")
    public void registerLocalMember() throws Exception {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("test456@gmail.com")
                .password("Pasword123!")
                .nickname("helloTest456")
                .build();

        mockMvc.perform(post("/auth/")
                .content(new ObjectMapper().writeValueAsString(signUpRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(AuthDocumentation.signUpLOCAL());
    }

    @Test
    @DisplayName("소셜 회원가입 진행")
    public void registerSocialMember() throws Exception {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("test123@gmail.com")
                .password("Pasword123!")
                .nickname("helloTest123")
                .social(Social.KAKAO)
                .userId("hello")
                .profileImage("Default Profile link")
                .build();

        mockMvc.perform(post("/auth/")
                .content(new ObjectMapper().writeValueAsString(signUpRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(AuthDocumentation.signUpSOCIAL());
    }

    @Test
    @DisplayName("소셜회원에 대한 정보값을 가져온다.")
    public void getSocialMember() throws Exception{
        //Given
        this.generateMember("socialMemberEmail",100,1);
        SocialMemberRequest socialMemberRequest = SocialMemberRequest.builder()
                .social(Social.KAKAO)
                .userId("kakao userId"+1)
                .build();
        //When & Then
        mockMvc.perform(post("/auth/social/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(socialMemberRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(AuthDocumentation.getSocialMember());
    }

    @Test
    @DisplayName("이메일 중복이 일어나지 않을때.")
    public void checkSignUpMemberEmail() throws Exception{
        //Given
        this.generateMember("testCheckEmail",200,2);
        CheckEmailRequest checkEmailRequest = CheckEmailRequest.builder()
                .email("accept@mail.com")
                .build();
        //When & Then
        mockMvc.perform(get("/auth/checkEmail/{email}",checkEmailRequest.email)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(checkEmailRequest)))
                .andExpect(result -> CheckEmailResponse.of(false))
                .andDo(print())
                .andDo(AuthDocumentation.getCheckEmail());
    }

    @Test
    @DisplayName("이메일 중복이 일어날때.")
    public void failCheckSignUpMemberEmail() throws Exception{
        //Given
        this.generateMember("testFailCheckEmail",400,3);
        CheckEmailRequest checkEmailRequest = CheckEmailRequest.builder()
                .email("testFailCheckEmail@gmail.com")
                .build();
        //When & Then
        mockMvc.perform(get("/auth/checkEmail/{email}",checkEmailRequest.email)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(checkEmailRequest)))
                .andExpect(result -> CheckEmailResponse.of(true))
                .andDo(print());
//                .andDo(AuthDocumentation.getCheckEmail());
    }

    private void generateMember(String emailId,int index, int uid){
        Member member = Member.builder()
                .userId("kakao userId"+uid)
                .social(Social.KAKAO)
                .profileImage("default profile Image url")
                .nickname("evan" + index)
                .password("testpwd1234")
                .email(emailId+"@gmail.com")
                .role(MemberRole.USER)
                .build();

        this.memberRepository.save(member);
    }
}



