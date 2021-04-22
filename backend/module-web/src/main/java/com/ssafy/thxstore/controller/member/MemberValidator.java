package com.ssafy.thxstore.controller.member;

import com.ssafy.thxstore.member.dto.MemberDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * EventValidator Bean 등록
 */
@Component
public class MemberValidator {
    public void validate(MemberDto memberDto, Errors errors){
    }
}
