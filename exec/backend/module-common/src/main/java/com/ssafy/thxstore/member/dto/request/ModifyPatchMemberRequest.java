package com.ssafy.thxstore.member.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Data
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

    private String address;

    private Double lat;

    private Double lon;

}
