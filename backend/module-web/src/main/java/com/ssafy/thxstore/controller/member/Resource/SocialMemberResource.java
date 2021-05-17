package com.ssafy.thxstore.controller.member.Resource;


import com.ssafy.thxstore.controller.member.AuthController;
import com.ssafy.thxstore.member.dto.response.SocialMemberResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


public class SocialMemberResource extends EntityModel<SocialMemberResponse> {
    public SocialMemberResource(SocialMemberResponse socialMemberResponse, Link... links) {
        super(socialMemberResponse, links);
        add(linkTo(AuthController.class).slash(socialMemberResponse.getCheck()).withSelfRel());
    }
}
