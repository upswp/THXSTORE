package com.ssafy.thxstore.controller.common;

import com.ssafy.thxstore.controller.security.UserPrincipal;
import com.ssafy.thxstore.member.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
	private Logger logger = LoggerFactory.getLogger(WithMockCustomUserSecurityContextFactory.class);
	public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		Member member = new Member();
		member.setId(customUser.id());
		member.setEmail(customUser.email());
		member.setPassword(customUser.password());
		member.setNickname(customUser.nickName());
		member.setProfileImage(customUser.profileImage());
		member.setRole(customUser.roles());
		member.setSocial(customUser.social());
		member.setPhoneNumber(customUser.phoneNumber());
		member.setUserId(customUser.userId());

		logger.info(String.valueOf(member));

		UserPrincipal principal = UserPrincipal.create(member);
		Authentication auth = new UsernamePasswordAuthenticationToken(principal, member.getPassword(),
			principal.getAuthorities());
		context.setAuthentication(auth);
		return context;
	}
}
