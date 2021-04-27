package com.ssafy.thxstore.controller.member.docs;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
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
        return document("signUp-member",
                links(
                        linkWithRel("self").description("link to self"),
                        linkWithRel("signUp-member").description("link to query members")
//                        linkWithRel("profile").description("link to profile")
                ),
                requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                ),
                requestFields(
                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("회원가입할 닉네임"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원가입할 E-mail"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("회원가입할 패스워드")
                ),
                responseHeaders(
                        headerWithName(HttpHeaders.LOCATION).description("Location header"),
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("hal+json type")
                ),
                relaxedResponseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원가입한 유저의 ID"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("유저 이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("유저 비밀번호"),
                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("유저 닉네임"),
                        fieldWithPath("_links.self.href").description("link to self"),
                        fieldWithPath("_links.signUp-member.href").description("link to query event list")
                )
        );
    }
}
