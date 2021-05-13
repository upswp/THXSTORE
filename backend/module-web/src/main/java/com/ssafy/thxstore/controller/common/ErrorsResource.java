package com.ssafy.thxstore.controller.common;

import com.ssafy.thxstore.common.exceptions.ErrorCode;
import com.ssafy.thxstore.controller.index.IndexController;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ErrorsResource extends EntityModel<ErrorCode> {
    public static EntityModel<ErrorCode> modelOf(ErrorCode errors) {
        EntityModel<ErrorCode> errorsModel = EntityModel.of(errors);
        errorsModel.add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
        return errorsModel;
    }
}
