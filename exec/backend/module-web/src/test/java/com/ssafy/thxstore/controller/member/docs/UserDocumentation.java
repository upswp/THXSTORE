package com.ssafy.thxstore.controller.member.docs;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultHandler;

import static com.ssafy.thxstore.controller.utils.ApiDocumentsUtils.getDocumentRequest;
import static com.ssafy.thxstore.controller.utils.ApiDocumentsUtils.getDocumentResponse;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

public class UserDocumentation {
    public static ResultHandler modifyPatchMember() {
        return document("patch-member",
                getDocumentRequest(),
                getDocumentResponse(),
                links(
                        linkWithRel("self").description("link to self"),
                        linkWithRel("patch-member").description("link to query members"),
                        linkWithRel("profile").description("link to profile")
                ),
                requestHeaders(
                        headerWithName("authorization").description("Bearer 토큰")),
                requestFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("수정할 user의 id"),
                        fieldWithPath("profileImage").type(JsonFieldType.NULL).description("수정할 profile 이미지"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("수정할 user의 닉네임"),
                        fieldWithPath("phoneNumber").type(JsonFieldType.NULL).description("수정할 user의 phoneNumber"),
                        fieldWithPath("password").type(JsonFieldType.NULL).description("수정할 user의 password")
                ),
                responseHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("hal+json type")
                ),
                relaxedResponseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("PK"),
                        fieldWithPath("profileImage").type(JsonFieldType.STRING).description("유저 비밀번호"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                        fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("유저 휴대폰번호"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("유저 비밀번호"),
                        fieldWithPath("_links.self.href").description("link to self"),
                        fieldWithPath("_links.patch-member").description("link to query event list")
                )
        );
    }
    public static ResultHandler modifyPutMember() {
        return document("put-member",
                getDocumentRequest(),
                getDocumentResponse(),
                links(
                        linkWithRel("self").description("link to self"),
                        linkWithRel("put-member").description("link to query members"),
                        linkWithRel("profile").description("link to profile")
                ),
                requestHeaders(
                        headerWithName("authorization").description("Bearer 토큰")),
                requestFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("수정할 user의 id"),
                        fieldWithPath("lat").type(JsonFieldType.NUMBER).description("수정할 user의 위도"),
                        fieldWithPath("lon").type(JsonFieldType.NUMBER).description("수정할 user의 경도")
                ),
                responseHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("hal+json type")
                ),
                relaxedResponseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("PK"),
                        fieldWithPath("lat").type(JsonFieldType.NUMBER).description("수정할 user의 위도"),
                        fieldWithPath("lon").type(JsonFieldType.NUMBER).description("수정할 user의 경도"),
                        fieldWithPath("_links.self.href").description("link to self"),
                        fieldWithPath("_links.put-member").description("link to query event list")
                )
        );
    }

    public static ResultHandler deleteMember() {
        return document("delete-member",
                getDocumentRequest(),
                getDocumentResponse(),
                links(
                        linkWithRel("self").description("link to self"),
                        linkWithRel("delete-member").description("link to query members"),
                        linkWithRel("profile").description("link to profile")
                ),
                requestHeaders(
                        headerWithName("authorization").description("Bearer 토큰")),
                responseHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("hal+json type")
                ),
                relaxedResponseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("PK"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("유저 이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("유저 비밀번호"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                        fieldWithPath("_links.self.href").description("link to self"),
                        fieldWithPath("_links.delete-member").description("link to query event list")
                )
        );
    }
}
