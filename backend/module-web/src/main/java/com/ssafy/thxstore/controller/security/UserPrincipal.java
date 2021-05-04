package com.ssafy.thxstore.controller.security;

import com.ssafy.thxstore.member.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class UserPrincipal implements UserDetails {
	private Member member;
	private Map<String, Object> attributes;

	private UserPrincipal(Member member) {
		this.member = member;
	}

	public static UserPrincipal create(Member member) {
		return new UserPrincipal(member);
	}

	public static UserPrincipal create(Member member, Map<String, Object> attributes) {
		UserPrincipal userPrincipal = UserPrincipal.create(member);
		userPrincipal.setAttributes(attributes);
		return userPrincipal;
	}

	public Member getUser() {
		return member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(member.roleName()));
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}