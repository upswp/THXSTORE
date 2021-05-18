package com.ssafy.thxstore.controller.order.docs;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultHandler;

import static com.ssafy.thxstore.controller.utils.ApiDocumentsUtils.getDocumentRequest;
import static com.ssafy.thxstore.controller.utils.ApiDocumentsUtils.getDocumentResponse;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class OrderDocumentation {

    public static ResultHandler createReview() {
        return document("create-review",
                getDocumentRequest(),
                getDocumentResponse(),
                links(
                        linkWithRel("self").description("link to self"),
                        linkWithRel("profile").description("link to profile")
                ),
                requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                ),
                requestFields(
                        fieldWithPath("comment").type(JsonFieldType.STRING).description("리뷰 내용"),
                        fieldWithPath("storeId").type(JsonFieldType.NUMBER).description("리뷰가 작성될 가게 아이디"),
                        fieldWithPath("storeName").type(JsonFieldType.STRING).description("리뷰가 작성될 가게 이름"),
                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("리뷰를 작성할 아이디"),
                        fieldWithPath("reservationId").type(JsonFieldType.NUMBER).description("리뷰가 달릴 주문 번호"),
                        fieldWithPath("star").type(JsonFieldType.NUMBER).description("별점"),
                        fieldWithPath("dateTime").type(JsonFieldType.STRING).description("리뷰 작성 시간"),
                        fieldWithPath("logo").type(JsonFieldType.STRING).description("로고 이미지 주소")
                ),
                responseHeaders(
                        headerWithName(HttpHeaders.LOCATION).description("Location header"),
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("hal+json type")
                ),
                relaxedResponseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("PK"),
                        fieldWithPath("comment").type(JsonFieldType.STRING).description("리뷰 내용"),
                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("유저 아이디"),
                        fieldWithPath("storeName").type(JsonFieldType.STRING).description("가게 이름"),
                        fieldWithPath("_links.self.href").description("link to self"),
                        fieldWithPath("_links.profile.href").description("link to profile")
                )
        );
    }

    public static ResultHandler deleteReview() {
        return document("delete-review",
                getDocumentRequest(),
                getDocumentResponse(),
                links(
                        linkWithRel("self").description("link to self"),
                        linkWithRel("delete-review").description("link to review"),
                        linkWithRel("profile").description("link to profile")
                ),
                requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                ),
                requestFields(
                        fieldWithPath("reviewId").type(JsonFieldType.NUMBER).description("삭제하고자하는 리뷰 아이디")
                ),
                responseHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("hal+json type")
                ),
                responseFields(
                        fieldWithPath("check").type(JsonFieldType.BOOLEAN).description("삭제여부 확인"),
                        fieldWithPath("_links.self.href").description("link to self"),
                        fieldWithPath("_links.profile.href").description("link to profile")
                )
        );
    }
}
