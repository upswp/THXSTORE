package com.ssafy.thxstore.controller.member;


import com.ssafy.thxstore.controller.common.ErrorsResource;
import com.ssafy.thxstore.controller.member.Resource.CheckEmailResource;
import com.ssafy.thxstore.controller.member.Resource.MemberResource;
import com.ssafy.thxstore.controller.member.Resource.SocialMemberResource;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.dto.request.CheckEmailRequest;
import com.ssafy.thxstore.member.dto.request.SignUpRequest;
import com.ssafy.thxstore.member.dto.request.SocialMemberRequest;
import com.ssafy.thxstore.member.dto.response.CheckEmailResponse;
import com.ssafy.thxstore.member.dto.response.SocialMemberResponse;
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
import javax.validation.constraints.Email;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping(value = "/auth", produces = MediaTypes.HAL_JSON_VALUE)
public class AuthController {

    private final MemberService memberService;

    @PostMapping
    @PreAuthorize("isAnonymous()")
    public ResponseEntity registerMember(@Valid @RequestBody SignUpRequest signUpRequest) {
        Member newMember = memberService.registerMember(signUpRequest);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(AuthController.class).slash(newMember.getId());
        URI createUri = selfLinkBuilder.toUri();
        MemberResource memberResource = new MemberResource(newMember);
        if (signUpRequest.getSocial() == null) {
            memberResource.add(linkTo(AuthController.class).withRel("signUp-LOCAL"));
            memberResource.add(Link.of("/api/docs/index.html#resources-signUp-LOCAL").withRel("profile"));
        } else {
            memberResource.add(linkTo(AuthController.class).withRel("signUp-SOCIAL"));
            memberResource.add(Link.of("/api/docs/index.html#resources-signUp-SOCIAL").withRel("profile"));
        }
        return ResponseEntity.created(createUri).body(memberResource);
    }


    @PostMapping("/social/")
    public ResponseEntity getSocialMember(@RequestBody @Valid SocialMemberRequest socialMemberRequest) {
        SocialMemberResponse socialMemberResponse = memberService.findSocialMember(socialMemberRequest);
        SocialMemberResource socialMemberResource = new SocialMemberResource(socialMemberResponse);
        socialMemberResource.add(linkTo(AuthController.class).withRel("find-social-member"));
        socialMemberResource.add(Link.of("/api/docs/index.html#resources-find-social-member").withRel("profile"));
        return ResponseEntity.ok(socialMemberResource);
    }


    @GetMapping("/checkEmail/{checkEmailRequest}")
    public ResponseEntity checkSignUpMemberEmail(@Valid @Email @PathVariable CheckEmailRequest checkEmailRequest){
        CheckEmailResponse checkEmailResponse = memberService.existsByEmail(checkEmailRequest);
        CheckEmailResource checkEmailResource= new CheckEmailResource(checkEmailResponse);
        checkEmailResource.add(linkTo(AuthController.class).withRel("check-member-email"));
        checkEmailResource.add(Link.of("/api/docs/index.html#resources-check-member-email").withRel("profile"));
        return ResponseEntity.ok(checkEmailResource);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(ErrorsResource.modelOf(errors));
    }
}
