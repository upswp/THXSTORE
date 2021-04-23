package com.ssafy.thxstore.controller.member;

import com.ssafy.thxstore.controller.common.ErrorsResource;
import com.ssafy.thxstore.member.dto.Member;
import com.ssafy.thxstore.member.dto.MemberDto;
import com.ssafy.thxstore.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.MediaTypes;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/member", produces = MediaTypes.HAL_JSON_VALUE)
public class MemberController {

    private final MemberService memberService;
    private final MemberValidator memberValidator;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/user/")
    public ResponseEntity registerMember(@Valid @RequestBody MemberDto memberDto,Errors errors){
        if(errors.hasErrors()){
            return badRequest(errors);
        }
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        Member member = memberService.registerMember(memberDto);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(MemberController.class).slash(member.getId());
        URI registerMemberUri = selfLinkBuilder.toUri();
        MemberResource memberResource = new MemberResource(member);
        memberResource.add(linkTo(MemberController.class).withRel("register-member"));
        memberResource.add(selfLinkBuilder.withRel("update-member"));
        //TODO:self-description 부분 추가하기
        memberResource.add(Link.of("").withRel("profile"));
        return ResponseEntity.created(registerMemberUri).body(memberResource);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(ErrorsResource.modelOf(errors));
    }
}
