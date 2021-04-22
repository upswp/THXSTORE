package com.ssafy.thxstore.controller.member;

import com.ssafy.thxstore.controller.common.ErrorsResource;
import com.ssafy.thxstore.member.dto.MemberDto;
import com.ssafy.thxstore.member.service.MemberService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity signupMember(@RequestBody @Valid MemberDto memberDto, Errors errors){
        if(errors.hasErrors()){
            return badRequest(errors);
        }
        memberService.saveMember(memberDto);
        return ResponseEntity.ok(memberDto);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(ErrorsResource.modelOf(errors));
    }
}
