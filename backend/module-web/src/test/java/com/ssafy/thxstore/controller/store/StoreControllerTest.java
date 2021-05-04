package com.ssafy.thxstore.controller.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.thxstore.controller.common.BaseControllerTest;
import com.ssafy.thxstore.controller.store.docs.AuthDocumentation;
import com.ssafy.thxstore.store.dto.CreateStoreDto;
import com.ssafy.thxstore.store.dto.CreateStoreFileDto;
import com.ssafy.thxstore.store.service.StoreService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StoreControllerTest extends BaseControllerTest {

    @Autowired
    StoreService storeService;
    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext,
               final RestDocumentationContextProvider restDocumentationContextProvider) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
    }

    @Test
    @DisplayName("스토어 생성()")
    public void createStore() throws Exception{
//        MockMultipartFile file = new MockMultipartFile("content", fileName.toString(), "multipart/mixed", content);
       // MultipartFile file = new MultipartFile();
      //  MultipartFile uploadFile = new MockMultipartFile("ssafy.jpg", "content".getBytes());
        CreateStoreDto createStoreDto = CreateStoreDto.builder()
                .name("SSAFY china food")
                .mainAddress("Yuseong-gu 98-39")
                .subAddress("Three castles 203-Ho")
                .phoneNum("042-820-7400")
                .license("111-11-11111")
                .licenseImg("asdkj12dj33fmds_ssafy_license_img.jpg")
                .build();

//        CreateStoreFileDto createStoreFileDto = CreateStoreFileDto.builder()
//                .name("SSAFY china food")
//                .mainAddress("Yuseong-gu 98-39")
//                .subAddress("Three castles 203-Ho")
//                .phoneNum("042-820-7400")
//                .license("111-11-11111")
//                .licenseImg(uploadFile)
//                .build();

        //MULTIPART_FORM_DATA 이지만, 테스트는?APPLICATION_JSON
        mockMvc.perform(post("/store/test/")
                .content(new ObjectMapper().writeValueAsString(createStoreDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(AuthDocumentation.createStore());
    }
}

//MediaType.APPLICATION_JSON
//MULTIPART_FORM_DATA