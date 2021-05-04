package com.ssafy.thxstore.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.thxstore.controller.common.BaseControllerTest;
import com.ssafy.thxstore.controller.member.docs.AuthDocumentation;
import com.ssafy.thxstore.member.domain.Social;
import com.ssafy.thxstore.member.dto.SignUpRequest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberControllerTest extends BaseControllerTest {

    @Autowired
    CustomMemberDetailsService memberService;

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext,
               final RestDocumentationContextProvider restDocumentationContextProvider) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
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
}



