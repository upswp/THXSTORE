package com.ssafy.thxstore.controller.store.docs;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.ssafy.thxstore.controller.utils.ApiDocumentsUtils.getDocumentRequest;
import static com.ssafy.thxstore.controller.utils.ApiDocumentsUtils.getDocumentResponse;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class AuthDocumentation {


    public static RestDocumentationResultHandler createStore() {
        return document("create-store",
                getDocumentRequest(),
                getDocumentResponse(),
                links(
                        linkWithRel("self").description("link to self"),
                        linkWithRel("create-store").description("link to query store"),
                        linkWithRel("profile").description("link to profile")
                ),
                requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                ),
                requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("store 이름"),
                        fieldWithPath("mainAddress").type(JsonFieldType.STRING).description("메인 주소(도로명)"),
                        fieldWithPath("subAddress").type(JsonFieldType.STRING).description("서브 주소(사용자 입력)"),
                        fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("가게 전화번호"),
                        fieldWithPath("license").type(JsonFieldType.STRING).description("사업자 등록번호"),
                        fieldWithPath("licenseImg").type(JsonFieldType.OBJECT).description("사업자 등록번호증")
                ),
                responseHeaders(
                        headerWithName(HttpHeaders.LOCATION).description("Location header"),
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("hal+json type")
                ),
                relaxedResponseFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("store 이름"),
                        fieldWithPath("mainAddress").type(JsonFieldType.STRING).description("메인 주소(도로명)"),
                        fieldWithPath("subAddress").type(JsonFieldType.STRING).description("서브 주소(사용자 입력)"),
                        fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("가게 전화번호"),
                        fieldWithPath("license").type(JsonFieldType.STRING).description("사업자 등록번호"),
                        fieldWithPath("licenseImg").type(JsonFieldType.STRING).description("사업자 등록번호증"),
                        fieldWithPath("_links.self.href").description("link to self"),
                        fieldWithPath("_links.create-store.href").description("link to query event list")
                )
        );
    }
}
