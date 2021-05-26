package com.ssafy.thxstore.member.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LoginRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
