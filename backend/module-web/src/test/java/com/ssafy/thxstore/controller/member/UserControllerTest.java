package com.ssafy.thxstore.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.thxstore.controller.common.BaseControllerTest;
import com.ssafy.thxstore.controller.config.AppProperties;
import com.ssafy.thxstore.controller.member.docs.UserDocumentation;
import com.ssafy.thxstore.member.dto.request.ModifyPatchMemberRequest;
import com.ssafy.thxstore.member.dto.request.SignUpRequest;
import com.ssafy.thxstore.member.repository.MemberRepository;
import com.ssafy.thxstore.member.service.AuthService;
import com.ssafy.thxstore.member.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.ssafy.thxstore.member.domain.Social.KAKAO;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    AppProperties appProperties;

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext,
               final RestDocumentationContextProvider restDocumentationContextProvider) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();

        this.memberRepository.deleteAll();
    }

    @Test
    @DisplayName("현재 유저의 정보를 수정한다.")
    public void modifyMember() throws Exception {
        ModifyPatchMemberRequest modifyPatchMemberRequest = ModifyPatchMemberRequest.builder()
                .id(1L)
                .nickname("IU")
                .build();

        this.mockMvc.perform(patch("/user/")
                .header("authorization",getExampleToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(modifyPatchMemberRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(UserDocumentation.modifyPatchMember());
    }

    @DisplayName("회원가입을 한다.")
    @Test
    public void registerUser() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest(
                "a@email.com",
                "password123",
                "evan",
                "image.com",
                KAKAO,
                "providerID"
                );

        mockMvc.perform(post("/auth/")
                .content(new ObjectMapper().writeValueAsBytes(signUpRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    public String getExampleToken(){
        return "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhQGVtYWlsLmNvbSIsImlhdCI6MTYyMDkzMTk3MSwiZXhwIjoxNjIwOTMyOTcxfQ.7ukdS2k8Ujyp3przJfsZolPmwfDg_5rt8g5HNz0ZI2kNl6_keX9D-6Ve7hkBQJSHGrgb2C-WgzKqcAZSpUATOQ";
    }
}
