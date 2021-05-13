package com.ssafy.thxstore.member.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ModifyPatchMemberRequest {

    @NotNull
    private Long id;

    private MultipartFile profileImage;

    private String nickname;

    private String phoneNumber;

    private String password;

}
