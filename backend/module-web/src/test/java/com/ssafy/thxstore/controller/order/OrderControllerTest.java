package com.ssafy.thxstore.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.thxstore.controller.common.BaseControllerTest;
import com.ssafy.thxstore.controller.member.docs.AuthDocumentation;
import com.ssafy.thxstore.controller.order.docs.OrderDocumentation;
import com.ssafy.thxstore.reservation.domain.Reservation;
import com.ssafy.thxstore.reservation.domain.Review;
import com.ssafy.thxstore.reservation.dto.ReviewDto;
import com.ssafy.thxstore.reservation.repository.ReservationRepository;
import com.ssafy.thxstore.reservation.service.ReviewService;
import com.ssafy.thxstore.store.domain.Store;
import com.ssafy.thxstore.store.dto.CreateStoreDto;
import com.ssafy.thxstore.store.repository.StoreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.DateFormat;
import java.util.Date;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest extends BaseControllerTest {

    @Autowired
    ReviewService reviewService;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    ReservationRepository reservationRepository;

    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
    DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT);
    String time = timeFormat.format(new Date());

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext,
               final RestDocumentationContextProvider restDocumentationContextProvider) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
    }

    @Test
    @DisplayName("리뷰 생성()")
    public void createReview() throws Exception{

        Store store1 = Store.builder().id(1L).name("storename").build();
        storeRepository.save(store1);

        Reservation reservation1 = Reservation.builder().storeId(1L).id(1L).build();
        reservationRepository.save(reservation1);

        ReviewDto reviewDto = ReviewDto.builder().
                reservationId(1L).
                dateTime(DateTime.now().toString()).
                comment("commenttest").
                star(4).
                memberId(1L).
                storeName("storenametest").
                storeId(1L).
                build();

        mockMvc.perform(post("/order/reservation/review")
                .content(new ObjectMapper().writeValueAsString(reviewDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(OrderDocumentation.createReview());
    }



}
