package com.ssafy.thxstore.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.thxstore.controller.common.BaseControllerTest;
import com.ssafy.thxstore.controller.member.docs.AuthDocumentation;
import com.ssafy.thxstore.member.dto.MemberDto;
import com.ssafy.thxstore.member.service.MemberService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class)
public class MemberControllerTest extends BaseControllerTest {


    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("MemberDto를 이용한 회원가입 진행")
    public void registerMember() throws Exception{
        MemberDto memberDto = MemberDto.builder()
                .email("test@gmail.com")
                .password("password123")
                .nickName("helloTest")
                .build();

        mockMvc.perform(post("/api/member/user/")
                .content(new ObjectMapper().writeValueAsString(memberDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(AuthDocumentation.signup());
    }

}



