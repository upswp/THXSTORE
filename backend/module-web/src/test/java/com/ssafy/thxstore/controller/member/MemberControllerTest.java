package com.ssafy.thxstore.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.thxstore.controller.common.RestDocsConfiguration;
import com.ssafy.thxstore.member.dto.MemberDto;
import com.ssafy.thxstore.member.dto.MemberRole;
import com.ssafy.thxstore.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@RequiredArgsConstructor
@Import(RestDocsConfiguration.class)
public class MemberControllerTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @MockBean
    private final MemberRepository memberRepository;

    @Test
    @DisplayName("정상적으로 멤버를 생성하는 테스트")
    public void createMember() throws Exception{
        MemberDto member = MemberDto.builder()
                .email("test@gmail.com")
                .password("TestWord1234")
                .build();
        mockMvc.perform(post("/api/member/user/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(member)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("memberType").value(MemberRole.USER))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.register-member").exists())
                .andDo(document("create-member",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("create-member").description("link to Signup member"),
                                linkWithRel("profile").description("link to profile")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        requestFields(
                                fieldWithPath("email").description("유저 이메일"),
                                fieldWithPath("password").description("유저 비밀번호"),
                                fieldWithPath("nickName").description("유저 닉네임")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location Header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("hal+json type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("identifier of new user"),
                                fieldWithPath("email").description("email of user"),
                                fieldWithPath("password").description("password of user"),
                                fieldWithPath("nickName").description("nickName of user"),
                                fieldWithPath("image").description("image of user"),
                                fieldWithPath("address").description("address of user"),
                                fieldWithPath("phoneNumber").description("phoneNumber of user"),
                                fieldWithPath("memberType").description("memberType of user"),

                                fieldWithPath("_links.self").description("link to self"),
                                fieldWithPath("_links.register-member").description("link to create-member"),
                                fieldWithPath("_links.profile").description("link to profile")
                        )
                ));
    }
}
