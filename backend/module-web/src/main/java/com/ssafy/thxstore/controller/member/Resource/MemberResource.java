package com.ssafy.thxstore.controller.member.Resource;


import com.ssafy.thxstore.controller.member.AuthController;
import com.ssafy.thxstore.member.domain.Member;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


public class MemberResource extends EntityModel<Member> {
    public MemberResource(Member member, Link... links) {
        super(member, links);
        add(linkTo(AuthController.class).slash(member.getId()).withSelfRel());
    }
}
