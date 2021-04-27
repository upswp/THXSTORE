package com.ssafy.thxstore.controller.member.docs;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class AuthDocumentation {
    public static RestDocumentationResultHandler login() {
        return document("auth/login",
                requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("로그인할 email"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("로그인할 password")
                ),
                responseFields(
                        fieldWithPath("tokenType").type(JsonFieldType.STRING).description("Token의 타입"),
                        fieldWithPath("accessToken").type(JsonFieldType.STRING).description("로그인으로 얻은 Token")
                )
        );
    }

    public static RestDocumentationResultHandler signup() {
        return document("auth/signup",
                requestFields(
                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("회원가입할 닉네임"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원가입할 E-mail"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("회원가입할 패스워드")
                ),
                responseHeaders(
                        headerWithName("Location").description("회원가입한 유저 Id")
                )
        );
    }
}
