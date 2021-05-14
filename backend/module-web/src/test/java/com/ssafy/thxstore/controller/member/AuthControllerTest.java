package com.ssafy.thxstore.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.thxstore.controller.common.BaseControllerTest;
import com.ssafy.thxstore.controller.member.docs.AuthDocumentation;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.domain.MemberRole;
import com.ssafy.thxstore.member.domain.Social;
import com.ssafy.thxstore.member.dto.request.SignUpRequest;
import com.ssafy.thxstore.member.dto.request.SocialMemberRequest;
import com.ssafy.thxstore.member.dto.response.CheckEmailResponse;
import com.ssafy.thxstore.member.repository.MemberRepository;
import com.ssafy.thxstore.member.service.AuthService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends BaseControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AuthService authService;



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
                .lat(37.33)
                .lon(126.59)
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
                .lat(37.33)
                .lon(126.59)
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
        this.generateMember("socialMemberEmail",200,1);
        SocialMemberRequest socialMemberRequest = SocialMemberRequest.builder()
                .social(Social.KAKAO)
                .userId("kakao userId"+1)
                .build();
        //When & Then
        mockMvc.perform(post("/auth/social/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(socialMemberRequest)))
                .andExpect(result -> CheckEmailResponse.of(true))
                .andDo(print())
                .andDo(AuthDocumentation.getSocialMember());
    }
    @Test
    @DisplayName("존재하지 않는 소셜회원에 대한 정보값을 가져온다.")
    public void getNotExistSocialMember() throws Exception{
        //Given
        this.generateMember("notExistSocialMemberMemberEmail",400,4);
        SocialMemberRequest socialMemberRequest = SocialMemberRequest.builder()
                .social(Social.KAKAO)
                .userId("not exist userId"+1)
                .build();
        //When & Then
        mockMvc.perform(post("/auth/social/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(socialMemberRequest)))
                .andExpect(result -> CheckEmailResponse.of(false))
                .andDo(print())
                .andDo(AuthDocumentation.getSocialMember());
    }

    @Test
    @DisplayName("이메일 중복이 일어나지 않을때.")
    public void checkSignUpMemberEmail() throws Exception{
        //Given
        String email = "test@gmail.com";
        this.generateMember("testExistCheckEmail",200,3);
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.set("email",email);
        //When & Then
        mockMvc.perform(get("/auth/checkEmail/").params(requestParam)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(requestParam)))
                .andExpect(result -> CheckEmailResponse.of(false))
                .andDo(print())
                .andDo(AuthDocumentation.getCheckEmail());
    }

    @Test
    @DisplayName("이메일 중복이 일어날때.")
    public void failCheckSignUpMemberEmail() throws Exception{
        //Given
        String email = "testCheckEmail@gmail.com";
        this.generateMember("testCheckEmail",400,2);
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.set("email",email);
        //When & Then
        mockMvc.perform(get("/auth/checkEmail/").params(requestParam)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestParam)))
                .andExpect(result -> CheckEmailResponse.of(true))
                .andDo(print())
                .andDo(AuthDocumentation.getCheckEmail());
    }

    private void generateMember(String emailId,int index, int uid){
        Member member = Member.builder()
                .userId("kakao userId"+uid)
                .social(Social.KAKAO)
                .profileImage("default profile Image url")
                .nickname("evan" + index)
                .password("testpwd1234")
                .email(emailId+"@gmail.com")
                .role(MemberRole.ROLE_USER)
                .lat(37.33)
                .lon(126.59)
                .build();

        this.memberRepository.save(member);
    }
}



