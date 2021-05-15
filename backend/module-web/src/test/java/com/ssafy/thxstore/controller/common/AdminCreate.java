package com.ssafy.thxstore.controller.common;

import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.domain.MemberRole;
import com.ssafy.thxstore.member.domain.Social;
import com.ssafy.thxstore.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Profile("test")
public class AdminCreate {
	public static final String ADMIN_NAME = "admin_evan";
	public static final String ADMIN_EMAIL = "admin@gamil.com";
	public static final String ADMIN_PASSWORD = "Password1!";

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public void execute() {
		Member user = new Member();
		user.setNickname(ADMIN_NAME);
		user.setEmail(ADMIN_EMAIL);
		user.setPassword(ADMIN_PASSWORD);
		user.setSocial(Social.LOCAL);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(MemberRole.ROLE_ADMIN);

		memberRepository.save(user);
	}
}
