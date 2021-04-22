package com.ssafy.thxstore.controller.member;


import com.ssafy.thxstore.member.dto.Member;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class MemberResource extends EntityModel<Member> {

    public MemberResource(Member member, Link... links) {
        super(member, links);
        // 아래 코드와 똑같은 의미 add(new Link("http://localhost:8080/api/events/" + event.getId()));
        add(linkTo(MemberController.class).slash(member.getUserId()).withSelfRel());
    }
}
