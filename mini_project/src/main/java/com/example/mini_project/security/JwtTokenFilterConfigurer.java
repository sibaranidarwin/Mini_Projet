package com.example.mini_project.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtTokenFilterConfigurer(JwtTokenProvider jwtTokenProvider,
                                    CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void configure(HttpSecurity http) {
        JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider, customUserDetailsService);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

