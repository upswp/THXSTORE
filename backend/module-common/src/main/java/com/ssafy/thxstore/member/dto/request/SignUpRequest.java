package com.ssafy.thxstore.member.dto.request;

import com.ssafy.thxstore.member.domain.Social;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpRequest {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String nickname;

    private String profileImage;

    private Social social;

    private String userId;

}
