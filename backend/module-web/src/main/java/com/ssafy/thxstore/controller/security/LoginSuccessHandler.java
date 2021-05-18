package com.ssafy.thxstore.controller.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.thxstore.configs.AppProperties;
import com.ssafy.thxstore.controller.config.JwtTokenProvider;
import com.ssafy.thxstore.member.dto.response.AuthResponse;
import com.ssafy.thxstore.util.CookieUtil;
import com.ssafy.thxstore.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	private final JwtTokenProvider jwtTokenProvider;
	private final CookieUtil cookieUtil;
	private final RedisUtil redisUtil;
	private final AppProperties appProperties;
	private Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		UserPrincipal  loginResponse = (UserPrincipal) authentication.getPrincipal();
		logger.info(String.valueOf(loginResponse));

		final String token = jwtTokenProvider.generateToken(authentication);
		final String refreshJwt = jwtTokenProvider.generateRefreshToken(authentication);
		Cookie accessToken = cookieUtil.createCookie(JwtTokenProvider.ACCESS_TOKEN_NAME, token);
		Cookie refreshToken = cookieUtil.createCookie(JwtTokenProvider.REFRESH_TOKEN_NAME, refreshJwt);
		redisUtil.setDataExpire(refreshJwt, authentication.getName(), appProperties.getAuth().getRefreshTokenExpirationMsec());
		response.addCookie(accessToken);
		response.addCookie(refreshToken);
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		new ObjectMapper().writeValue(response.getWriter(),
				new AuthResponse(
						jwtTokenProvider.generateToken(authentication),
						jwtTokenProvider.generateRefreshToken(authentication),
						loginResponse.getUser()
				));
	}
}
