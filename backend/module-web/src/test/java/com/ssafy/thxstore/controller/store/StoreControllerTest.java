package com.ssafy.thxstore.controller.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.thxstore.controller.common.BaseControllerTest;
import com.ssafy.thxstore.controller.store.docs.AuthDocumentation;
import com.ssafy.thxstore.store.dto.CreateStoreDto;
import com.ssafy.thxstore.store.service.StoreService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
        CreateStoreDto createStoreDto = CreateStoreDto.builder()
                .name("SSAFY tset test222")
                .mainAddress("tes22t 98-39")
                .subAddress("tttt 203tt")
                .phoneNum("042-820-7400")
                .license("111-11-11111")
                .licenseImg("licenseasdc123sdfa23f_ssafylicense.jpg")
                .build();
//        SignUpRequest signUpRequest = SignUpRequest.builder()
//                .email("test123@gmail.com")
//                .password("Pasword123!")
//                .nickName("helloTest123")
//                .build();


        //MULTIPART_FORM_DATA 이지만, 테스트는?
        System.out.println(0);
        mockMvc.perform(post("/store/")
                .content(new ObjectMapper().writeValueAsString(createStoreDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(AuthDocumentation.createStore());
        System.out.println("2");
    }
}

//MediaType.APPLICATION_JSON
//MULTIPART_FORM_DATA