//package com.ssafy.thxstore.controller.member;
//
//import com.ssafy.thxstore.controller.common.AcceptanceTest;
//import com.ssafy.thxstore.configs.AppProperties;
//import com.ssafy.thxstore.controller.member.docs.UserDocumentation;
//import com.ssafy.thxstore.member.dto.request.LoginRequest;
//import com.ssafy.thxstore.member.dto.request.ModifyPatchMemberRequest;
//import com.ssafy.thxstore.member.dto.request.ModifyPutMemberRequest;
//import com.ssafy.thxstore.member.repository.MemberRepository;
//import com.ssafy.thxstore.member.service.AuthService;
//import com.ssafy.thxstore.member.service.UserService;
//import io.restassured.response.ExtractableResponse;
//import io.restassured.response.Response;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.RestDocumentationContextProvider;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static io.restassured.RestAssured.given;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class UserControllerTest extends AcceptanceTest {
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    AuthService authService;
//
//    @Autowired
//    AppProperties appProperties;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @BeforeEach
//    void setUp(final WebApplicationContext webApplicationContext,
//               final RestDocumentationContextProvider restDocumentationContextProvider) {
//
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(documentationConfiguration(restDocumentationContextProvider))
//                .build();
//
//        this.memberRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("현재 유저의 정보를 일부 수정한다.")
//    public void modifyPatchMember() throws Exception {
//        String email = "modifypatchmember@email.com";
//        generateMember(email,"modifypatchmember");
//        ModifyPatchMemberRequest modifyPatchMemberRequest = ModifyPatchMemberRequest.builder()
//                .id(1L)
//                .nickname("IU")
//                .password(null)
//                .build();
//
//        this.mockMvc.perform(patch("/user/")
//                .header("authorization", getExampleToken(email))
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(this.objectMapper.writeValueAsString(modifyPatchMemberRequest)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andDo(UserDocumentation.modifyPatchMember());
//    }
//
//    @Test
//    @DisplayName("현재 유저의 위치 정보를 수정한다.")
//    public void modifyPutMember() throws Exception {
//        String email = "modifyput@email.com";
//        generateMember(email,"modifyLatAndLot");
//        ModifyPutMemberRequest modifyPutMemberRequest = ModifyPutMemberRequest.builder()
//                .id(1L)
//                .lat(39.00)
//                .lon(130.00)
//                .build();
//
//        this.mockMvc.perform(put("/user/")
//                .header("authorization", getExampleToken(email))
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(this.objectMapper.writeValueAsString(modifyPutMemberRequest)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andDo(UserDocumentation.modifyPutMember());
//    }
//
//    @Test
//    @DisplayName("현재 유저의 정보를 삭제한다.")
//    public void deleteMember() throws Exception {
//        String email = "delete@gmail.com";
//        generateMember(email,"deleteMember");
//        this.mockMvc.perform(delete("/user/")
//                .header("authorization", getExampleToken("delete@gmail.com"))
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(this.objectMapper.writeValueAsString(email)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andDo(UserDocumentation.deleteMember());
//    }
//
//    /**
//     * 로그인 시 LoginRequest로 로그인 요청을 한 후 토큰을 가져온다.
//     * @param email 로그인하는 유저의 이메일
//     * @return 로그인한 유저의 토큰 값
//     */
//    private String getExampleToken(String email){
//        LoginRequest loginRequest = new LoginRequest(email, "password123");
//        ExtractableResponse<Response> response = requestLogin(loginRequest);
//        return response.body().jsonPath().getString("accessToken");
//    }
//
//    /**
//     * 로그인 시도를 한다.
//     * @param loginRequest 로그인 요청 dto
//     * @return 로그인 결과 정보, 토큰, 토큰정보, 유저 정보
//     */
//    private static ExtractableResponse<Response> requestLogin(LoginRequest loginRequest) {
//        return given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(loginRequest)
//                .when()
//                .post("/api/auth/login/")
//                .then().log().all()
//                .extract();
//    }
//}
