package com.ssafy.thxstore.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "/^[A-Za-z0-9+]{6,12}$/")
    private String password;

    @NotEmpty
    private String nickName;

}
