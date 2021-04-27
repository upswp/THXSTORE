package com.ssafy.thxstore.controller.member;


import com.ssafy.thxstore.member.domain.Member;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


public class MemberResource extends EntityModel<Member> {
    public MemberResource(Member member, Link... links) {
        super(member, links);
        add(linkTo(MemberController.class).slash(member.getId()).withSelfRel());
    }
}
