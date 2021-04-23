package com.ssafy.thxstore.controller.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * TDD 방식은 최소 3가지 삼각기법? 을 이용해서 처리를 한다고 한다.
 * 어떤 기준으로 3가지를 하는건지 알아보자.
 */
//Junit4 기준
@RunWith(SpringRunner.class)
/**
 * 통합 테스트로 전환, @WebMvcTest를 빼고 아래의 어노테이션을 작성한다.
 * SpringBootTest 어노테이션을 이용해서 테스트를 진행하면 이제 실제 Repository를 이용해서 Test가 진행이된다.
 * 테스트에 넣어 준 값들은 무시가 된다.
 * 통합테스트로 작업을 하게 될 경우 단위테스트로 작업해야하는 Mocking해줘야 하는 값들이 많아져서 테스트 코드를 작성하기 힘들다.
 * 코드가 바뀔때 마다 테스트가 계속 깨지기 때문이다.
 * SpringBootTest Annotation을 사용하면 @SpringBootApplication 어노테이션을 찾아서 설정에 따라 모든 Bean들이 등록 되도록 한다.
 * 이때 , 애플리케이션과 가장 가까운 테스트를 만들어 작성할 수 있다. 슬라이싱 테스트 보다 테스트 작업하는게 더 수월하다.
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@RequiredArgsConstructor
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
@Ignore
public class BaseControllerTest {
    /**
     * 스프링 MVC 테스트 핵심 클래스//웹 서버를 띄우지 않고도
     * 스프링MVC(DispatcherServlet)가 요청을 처리하는 과정에서
     * 확인할 수 있기 떄문에 컨트롤러 테스트용으로 자주 쓰인다.
     * 간단하게 요청을 만들 수 있고 결과를 확인할 수 있다.
     * 웹서버를 띄우지 않기 떄문에 조금 더 빠르다. 하지만 단위 테스트에 비해서는 만드는 객체들이 많기 떄문에 초반에 구동되는 부분들이 많다.
     * 단위테스트 보다는 좀 더 걸리는 중간 속도이다.
     */
    @Autowired
    protected MockMvc mockMvc;

    /**
     * 객체를 JSON으로 변환
     */
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ModelMapper modelMapper;
}
