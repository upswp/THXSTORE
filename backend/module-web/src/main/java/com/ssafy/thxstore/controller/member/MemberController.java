package com.ssafy.thxstore.controller.member;

import com.ssafy.thxstore.controller.common.ErrorsResource;
import com.ssafy.thxstore.domain.Member;
import com.ssafy.thxstore.domain.SignupRequest;
import com.ssafy.thxstore.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.RequestEntity;
import org.springframework.validation.Errors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.MediaTypes;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/member", produces = MediaTypes.HAL_JSON_VALUE)
public class MemberController {

    private final MemberService memberService;
    private final MemberValidator memberValidator;

    @PostMapping("/user/")
    public ResponseEntity signupMember(@RequestBody @Valid SignupRequest signupRequest, Errors errors){
        if(errors.hasErrors()){
            return badRequest(errors);
        }
        memberService.saveMember(signupRequest);
        return ResponseEntity.ok(signupRequest);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(ErrorsResource.modelOf(errors));
    }
}
