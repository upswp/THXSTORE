package com.ssafy.thxstore.controller.member.docs;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultHandler;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

public class UserDocumentation {
    public static ResultHandler modifyPatchMember() {
        return document("patch-member",
                requestHeaders(
                        headerWithName("authorization").description("Bearer 토큰")),
                requestFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("수정할 user의 id"),
                        fieldWithPath("profileImage").type(JsonFieldType.OBJECT).description("수정할 profile 이미지"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("수정할 user의 닉네임"),
                        fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("수정할 user의 phoneNumber"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("수정할 user의 password")
                )
        );
    }
}
