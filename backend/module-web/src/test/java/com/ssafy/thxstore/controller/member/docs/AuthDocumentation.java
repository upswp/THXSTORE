package com.ssafy.thxstore.controller.member.docs;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultHandler;

import static com.ssafy.thxstore.controller.utils.ApiDocumentsUtils.getDocumentRequest;
import static com.ssafy.thxstore.controller.utils.ApiDocumentsUtils.getDocumentResponse;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class AuthDocumentation {
    public static RestDocumentationResultHandler login() {
        return document("login-member",
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

    public static RestDocumentationResultHandler signUpLOCAL() {
        return document("signUp-LOCAL",
                getDocumentRequest(),
                getDocumentResponse(),
                links(
                        linkWithRel("self").description("link to self"),
                        linkWithRel("signUp-LOCAL").description("link to query members"),
                        linkWithRel("profile").description("link to profile")
                ),
                requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                ),
                requestFields(
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원가입할 닉네임"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원가입할 E-mail"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("회원가입할 패스워드")
                ).and(subsectionWithPath("profileImage").type(JsonFieldType.NULL).description("회원 프로필 이미지"),
                        subsectionWithPath("social").type(JsonFieldType.NULL).description("회원 소셜 종류 판단"),
                        subsectionWithPath("userId").type(JsonFieldType.NULL).description("소셜 유저 아이디")),
                responseHeaders(
                        headerWithName(HttpHeaders.LOCATION).description("Location header"),
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("hal+json type")
                ),
                relaxedResponseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("PK"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("유저 이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("유저 비밀번호"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                        fieldWithPath("_links.self.href").description("link to self"),
                        fieldWithPath("_links.signUp-LOCAL.href").description("link to query event list")
                )
        );
    }

    public static ResultHandler signUpSOCIAL() {
        return document("signUp-SOCIAL",
                getDocumentRequest(),
                getDocumentResponse(),
                links(
                        linkWithRel("self").description("link to self"),
                        linkWithRel("signUp-SOCIAL").description("link to query members"),
                        linkWithRel("profile").description("link to profile")
                ),
                requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                ),
                requestFields(
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원가입할 닉네임"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원가입할 E-mail"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("회원가입할 패스워드"),
                        fieldWithPath("profileImage").type(JsonFieldType.STRING).description("회원 프로필 이미지"),
                        fieldWithPath("social").type(JsonFieldType.STRING).description("회원 소셜 종류 판단"),
                        fieldWithPath("userId").type(JsonFieldType.STRING).description("소셜 유저 아이디")
                ),
                responseHeaders(
                        headerWithName(HttpHeaders.LOCATION).description("Location header"),
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("hal+json type")
                ),
                relaxedResponseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("PK"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("유저 이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("유저 비밀번호"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                        fieldWithPath("profileImage").type(JsonFieldType.STRING).description("회원 프로필 이미지"),
                        fieldWithPath("social").type(JsonFieldType.STRING).description("회원 소셜 종류 판단"),
                        fieldWithPath("userId").type(JsonFieldType.STRING).description("소셜 유저 아이디"),
                        fieldWithPath("_links.self.href").description("link to self"),
                        fieldWithPath("_links.signUp-SOCIAL.href").description("link to query event list"),
                        fieldWithPath("_links.profile.href").description("link to profile")
                )
        );
    }

    public static ResultHandler getSocialMember() {
        return document("find-social-member",
                getDocumentRequest(),
                getDocumentResponse(),
                links(
                        linkWithRel("self").description("link to self"),
                        linkWithRel("find-social-member").description("link to query members"),
                        linkWithRel("profile").description("link to profile")
                ),
                requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                ),
                requestFields(
                        fieldWithPath("social").type(JsonFieldType.STRING).description("회원 소셜 종류 판단"),
                        fieldWithPath("userId").type(JsonFieldType.STRING).description("소셜 유저 아이디")
                ),
                responseHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("hal+json type")
                ),
                responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("PK"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("유저 이메일"),
                        fieldWithPath("profileImage").type(JsonFieldType.STRING).description("회원 프로필 이미지"),
                        fieldWithPath("_links.self.href").description("link to self"),
                        fieldWithPath("_links.find-social-member.href").description("link to query event list"),
                        fieldWithPath("_links.profile.href").description("link to profile")
                )
        );
    }
}
