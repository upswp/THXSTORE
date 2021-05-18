package com.ssafy.thxstore.controller.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.thxstore.common.exceptions.AuthException;
import com.ssafy.thxstore.common.exceptions.ErrorCode;
import com.ssafy.thxstore.member.dto.request.LoginRequest;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
            AuthenticationException {

        LoginRequest loginRequest;

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        try {
            loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            throw new AuthException(ErrorCode.BAD_LOGIN);
        }

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);


        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}