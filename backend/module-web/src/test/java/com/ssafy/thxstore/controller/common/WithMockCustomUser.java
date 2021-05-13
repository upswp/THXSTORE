package com.ssafy.thxstore.controller.common;

import com.ssafy.thxstore.member.domain.MemberRole;
import com.ssafy.thxstore.member.domain.Social;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    long id() default 1L;

    String email() default "a@email.com";

    String profileImage() default "image.com";

    String nickName() default "evan";

    String password() default "password123";

    Social social() default Social.KAKAO;

    MemberRole roles() default MemberRole.ROLE_USER;

    String userId() default "providerID";

    String phoneNumber() default "010-1234-1234";

}
