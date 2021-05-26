package com.ssafy.thxstore.controller.member;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.thxstore.controller.member.Resource.CheckEmailResource;
import com.ssafy.thxstore.controller.member.Resource.MemberResource;
import com.ssafy.thxstore.controller.member.Resource.SocialMemberResource;
import com.ssafy.thxstore.email.EmailService;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.dto.request.*;
import com.ssafy.thxstore.member.dto.response.CheckEmailResponse;
import com.ssafy.thxstore.member.dto.response.SocialMemberResponse;
import com.ssafy.thxstore.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping(value = "/auth", produces = MediaTypes.HAL_JSON_VALUE)
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping
    @PreAuthorize("isAnonymous()")
    public ResponseEntity registerMember(@Valid @RequestBody SignUpRequest signUpRequest) {
        Member newMember = authService.registerMember(signUpRequest);

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
        SocialMemberResponse socialMemberResponse = authService.findSocialMember(socialMemberRequest);
        SocialMemberResource socialMemberResource = new SocialMemberResource(socialMemberResponse);
        socialMemberResource.add(linkTo(AuthController.class).withRel("find-social-member"));
        socialMemberResource.add(Link.of("/api/docs/index.html#resources-find-social-member").withRel("profile"));
        return ResponseEntity.ok(socialMemberResource);
    }


    @GetMapping("/checkEmail/")
    public ResponseEntity checkSignUpMemberEmail(@Valid @RequestParam Map<String, String> parameterMap){
        ObjectMapper objectMapper = new ObjectMapper();
        CheckEmailRequest checkEmailRequest = objectMapper.convertValue(parameterMap,CheckEmailRequest.class);
        CheckEmailResponse checkEmailResponse = authService.existsByEmail(checkEmailRequest);
        CheckEmailResource checkEmailResource= new CheckEmailResource(checkEmailResponse);
        checkEmailResource.add(linkTo(AuthController.class).withRel("check-member-email"));
        checkEmailResource.add(Link.of("/api/docs/index.html#resources-check-member-email").withRel("profile"));
        return ResponseEntity.ok(checkEmailResource);
    }

    @PostMapping("/email/")
    public ResponseEntity emailAuth(@RequestBody SendEmailRequest sendEmailRequest) throws Exception {
        CheckEmailResponse checkEmailResponse = emailService.sendSimpleMessage(sendEmailRequest.getEmail());
        CheckEmailResource checkEmailResource= new CheckEmailResource(checkEmailResponse);
        checkEmailResource.add(linkTo(AuthController.class).withRel("send-member-email"));
        checkEmailResource.add(Link.of("/api/docs/index.html#resources-send-member-email").withRel("profile"));
        return ResponseEntity.ok(checkEmailResource);
    }

    @PostMapping("/email/resend/")
    public ResponseEntity reSendEmailAuth(@RequestBody SendEmailRequest sendEmailRequest) throws Exception {
        CheckEmailResponse checkEmailResponse = emailService.sendSimpleMessage(sendEmailRequest.getEmail());
        CheckEmailResource checkEmailResource= new CheckEmailResource(checkEmailResponse);
        checkEmailResource.add(linkTo(AuthController.class).withRel("send-member-email"));
        checkEmailResource.add(Link.of("/api/docs/index.html#resources-send-member-email").withRel("profile"));
        return ResponseEntity.ok(checkEmailResource);
    }

    @PostMapping("/verifyCode/")
    public ResponseEntity verifyCode(@RequestBody CheckEmailCodeRequest checkEmailCodeRequest) {
        CheckEmailResponse checkEmailResponse;
        if(EmailService.ePw.equals(checkEmailCodeRequest.getCode())) {
            checkEmailResponse = CheckEmailResponse.of(true);
        }
        else{
            checkEmailResponse = CheckEmailResponse.of(false);
        }
        CheckEmailResource checkEmailResource= new CheckEmailResource(checkEmailResponse);
        checkEmailResource.add(linkTo(AuthController.class).withRel("check-send-member-email"));
        checkEmailResource.add(Link.of("/api/docs/index.html#resources-check-send-member-email").withRel("profile"));
        return ResponseEntity.ok(checkEmailResource);
    }

}
