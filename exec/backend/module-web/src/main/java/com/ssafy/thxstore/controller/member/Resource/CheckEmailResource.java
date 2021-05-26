package com.ssafy.thxstore.controller.member.Resource;

import com.ssafy.thxstore.controller.member.AuthController;
import com.ssafy.thxstore.member.dto.response.CheckEmailResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class CheckEmailResource extends EntityModel<CheckEmailResponse> {
    public CheckEmailResource(CheckEmailResponse checkEmailResponse, Link... links) {
        super(checkEmailResponse, links);
        add(linkTo(AuthController.class).slash(checkEmailResponse.getCheck()).withSelfRel());
    }
}
