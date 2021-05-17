package com.ssafy.thxstore.controller.order.Resource;

import com.ssafy.thxstore.controller.member.AuthController;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.reservation.domain.Review;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class ReviewResource extends EntityModel<Review>{
    public ReviewResource(Review review, Link... links) {
        super(review, links);
        add(linkTo(AuthController.class).slash(review.getId()).withSelfRel());
    }
}
