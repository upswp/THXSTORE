package com.ssafy.thxstore.controller.member;


import com.ssafy.thxstore.controller.common.ErrorsResource;
import com.ssafy.thxstore.member.dto.MemberDto;
import com.ssafy.thxstore.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/member", produces = MediaTypes.HAL_JSON_VALUE)
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/user/")
    public ResponseEntity registerMember(@Valid @RequestBody MemberDto memberDto, Errors errors){
        Long memberId = memberService.registerMember(memberDto);
        return ResponseEntity.created(URI.create("/api/member/user/" + memberId)).build();
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(ErrorsResource.modelOf(errors));
    }
}
