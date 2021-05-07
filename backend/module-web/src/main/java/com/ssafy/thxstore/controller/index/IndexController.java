package com.ssafy.thxstore.controller.index;

import com.ssafy.thxstore.controller.member.AuthController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class IndexController {
    @GetMapping("/")
    public RepresentationModel index() {
        var index = new RepresentationModel();
        index.add(linkTo(AuthController.class).withRel("member"));
        return index;
    }
}

