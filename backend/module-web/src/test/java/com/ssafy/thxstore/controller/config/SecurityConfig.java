package com.ssafy.thxstore.controller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //authorization
        http.authorizeRequests()
                .mvcMatchers("/","info")
                .permitAll()
                .mvcMatchers("/admin")
                .hasRole("ADMIN")
                .anyRequest().authenticated();

        http.formLogin()
                .and()
                .httpBasic();
    }
}
