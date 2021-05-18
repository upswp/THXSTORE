package com.ssafy.thxstore.controller.security;

import com.ssafy.thxstore.controller.config.JwtTokenProvider;
import com.ssafy.thxstore.controller.member.CustomMemberDetailsService;
import com.ssafy.thxstore.util.CookieUtil;
import com.ssafy.thxstore.util.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomMemberDetailsService customMemberDetailsService;
    @Autowired
    private CookieUtil cookieUtil;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final Cookie jwtToken = cookieUtil.getCookie(request, JwtTokenProvider.ACCESS_TOKEN_NAME);
        String refreshJwt = null;
        String jwt = null;
        String username = null;
        try {
            if (jwtToken != null) {
                jwt = getJwtFromRequest(request);
                username = jwtTokenProvider.getUsername(jwt);
            }

            if (username != null && StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {

                String userEmail = jwtTokenProvider.getUsername(jwt);

                UserDetails userDetails = customMemberDetailsService.loadUserByUsername(userEmail);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException ex) {
            Cookie refreshToken = cookieUtil.getCookie(request, JwtTokenProvider.REFRESH_TOKEN_NAME);
            if (refreshToken != null) {
                refreshJwt = refreshToken.getValue();
            }
        }
        try {
            if (refreshJwt != null) {
                String refreshUsername = redisUtil.getData(refreshJwt);

                if (refreshUsername.equals(jwtTokenProvider.getUsername(refreshJwt))) {
                    UserDetails userDetails = customMemberDetailsService.loadUserByUsername(refreshUsername);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    String newToken = jwtTokenProvider.generateToken(authentication);
                    Cookie newAccessToken = cookieUtil.createCookie(JwtTokenProvider.ACCESS_TOKEN_NAME, newToken);
                    response.addCookie(newAccessToken);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
