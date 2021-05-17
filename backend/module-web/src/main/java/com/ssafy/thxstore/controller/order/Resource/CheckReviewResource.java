package com.ssafy.thxstore.controller.order.Resource;

import com.ssafy.thxstore.controller.member.AuthController;
import com.ssafy.thxstore.controller.order.OrderController;
import com.ssafy.thxstore.member.dto.response.CheckEmailResponse;
import com.ssafy.thxstore.reservation.dto.response.CheckReviewResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class CheckReviewResource extends EntityModel<CheckReviewResponse> {
    public CheckReviewResource(CheckReviewResponse checkReviewResponse, Link... links) {
        super(checkReviewResponse, links);
        add(linkTo(OrderController.class).slash(checkReviewResponse.getCheck()).withSelfRel());
    }
}
