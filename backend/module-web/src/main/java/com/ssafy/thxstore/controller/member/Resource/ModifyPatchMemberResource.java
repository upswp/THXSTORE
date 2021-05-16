package com.ssafy.thxstore.controller.member.Resource;

import com.ssafy.thxstore.controller.member.AuthController;
import com.ssafy.thxstore.member.dto.response.ModifyPatchMemberResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class ModifyPatchMemberResource extends EntityModel<ModifyPatchMemberResponse>  {
    public ModifyPatchMemberResource(ModifyPatchMemberResponse modifyPatchMemberResponse, Link... links) {
        super(modifyPatchMemberResponse, links);
        add(linkTo(AuthController.class).slash(modifyPatchMemberResponse.getId()).withSelfRel());
    }
}