package com.ssafy.thxstore.controller.member;


import com.ssafy.thxstore.member.domain.Member;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class SingUpResource extends EntityModel<Long> {

    private Member member;
    private Long memberId = member.getId();

    public SingUpResource(Long memberId) {
        this.memberId = memberId;
    }

    public SingUpResource(Long memberId, Link... links) {
        super(memberId, links);
        add(linkTo(MemberController.class).slash(member).withSelfRel());
    }
}
