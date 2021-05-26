package com.ssafy.thxstore.controller.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.thxstore.controller.config.JwtTokenProvider;
import com.ssafy.thxstore.member.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	private final JwtTokenProvider jwtTokenProvider;
	private Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		UserPrincipal  loginResponse = (UserPrincipal) authentication.getPrincipal();
		logger.info(String.valueOf(loginResponse));
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		new ObjectMapper().writeValue(response.getWriter(),
				new AuthResponse(
						jwtTokenProvider.createToken(authentication),
						loginResponse.getUser()
				));
	}
}
