package com.ssafy.thxstore.controller.member.Resource;

import com.ssafy.thxstore.controller.member.AuthController;
import com.ssafy.thxstore.member.dto.response.ModifyPutMemberResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class ModifyPutMemberResource extends EntityModel<ModifyPutMemberResponse> {
    public ModifyPutMemberResource(ModifyPutMemberResponse modifyPutMemberResponse, Link... links) {
        super(modifyPutMemberResponse, links);
        add(linkTo(AuthController.class).slash(modifyPutMemberResponse.getId()).withSelfRel());
    }
}
