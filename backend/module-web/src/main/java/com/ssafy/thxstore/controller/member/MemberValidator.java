package com.ssafy.thxstore.controller.member;

import com.ssafy.thxstore.domain.SignupRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

/**
 * EventValidator Bean 등록
 */
@Component
public class MemberValidator {
    public void validate(SignupRequest signupRequest, Errors errors){
    }
}
