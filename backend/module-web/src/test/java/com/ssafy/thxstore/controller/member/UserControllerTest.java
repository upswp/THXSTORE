package com.ssafy.thxstore.controller.member;

import com.ssafy.thxstore.controller.common.AcceptanceTest;
import com.ssafy.thxstore.controller.config.AppProperties;
import com.ssafy.thxstore.controller.member.docs.UserDocumentation;
import com.ssafy.thxstore.member.dto.request.LoginRequest;
import com.ssafy.thxstore.member.dto.request.ModifyPatchMemberRequest;
import com.ssafy.thxstore.member.dto.request.SignUpRequest;
import com.ssafy.thxstore.member.repository.MemberRepository;
import com.ssafy.thxstore.member.service.AuthService;
import com.ssafy.thxstore.member.service.UserService;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.ssafy.thxstore.member.domain.Social.KAKAO;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AcceptanceTest {
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
        generateMember();
        ModifyPatchMemberRequest modifyPatchMemberRequest = ModifyPatchMemberRequest.builder()
                .id(1L)
                .nickname("IU")
                .build();

        this.mockMvc.perform(patch("/user/")
                .header("authorization", getExampleToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(modifyPatchMemberRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(UserDocumentation.modifyPatchMember());
    }

    private void generateMember() {
        SignUpRequest signUpRequest = new SignUpRequest(
                "a@email.com",
                "password123",
                "evan",
                "image.com",
                KAKAO,
                "providerID"
        );

        this.authService.registerMember(signUpRequest);
    }



    public String getExampleToken(){
        LoginRequest loginRequest = new LoginRequest("a@email.com", "password123");

        ExtractableResponse<Response> response = requestLogin(loginRequest);
        return response.body().jsonPath().getString("accessToken");
    }

    public static ExtractableResponse<Response> requestLogin(LoginRequest loginRequest) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when()
                .post("/api/auth/login/")
                .then().log().all()
                .extract();
    }
}
