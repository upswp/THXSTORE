package com.ssafy.thxstore.controller.member;


import com.ssafy.thxstore.controller.common.ErrorsResource;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.dto.SignUpRequest;
import com.ssafy.thxstore.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping(value = "/auth", produces = MediaTypes.HAL_JSON_VALUE)
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @PreAuthorize("isAnonymous()")
    public ResponseEntity registerMember(@Valid @RequestBody SignUpRequest signUpRequest, Errors errors){
        if(errors.hasErrors()){
            return badRequest(errors);
        }
        Member newMember = memberService.registerMember(signUpRequest);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(MemberController.class).slash(newMember.getId());
        URI createUri = selfLinkBuilder.toUri();
        MemberResource memberResource = new MemberResource(newMember);
        if(signUpRequest.getSocial() == null){
            memberResource.add(linkTo(MemberController.class).withRel("signUp-LOCAL"));
            memberResource.add(Link.of("/api/docs/index.html#resources-signUp-LOCAL").withRel("profile"));
        }else{
            memberResource.add(linkTo(MemberController.class).withRel("signUp-SOCIAL"));
            memberResource.add(Link.of("/api/docs/index.html#resources-signUp-SOCIAL").withRel("profile"));
        }
        return ResponseEntity.created(createUri).body(memberResource);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(ErrorsResource.modelOf(errors));
    }
}
