package com.ssafy.thxstore.controller.member;


import com.ssafy.thxstore.member.dto.SocialMemberResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


public class SocialMemberResource extends EntityModel<SocialMemberResponse> {
    public SocialMemberResource(SocialMemberResponse socialMemberResponse, Link... links) {
        super(socialMemberResponse, links);
        add(linkTo(MemberController.class).slash(socialMemberResponse.getId()).withSelfRel());
    }
}
