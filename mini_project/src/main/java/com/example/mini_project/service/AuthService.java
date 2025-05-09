package com.example.mini_project.service;

import com.example.mini_project.dto.auth.LoginResponse;
import com.example.mini_project.dto.auth.LoginrRequest;
import com.example.mini_project.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponse login(LoginrRequest loginRequest) {
        LoginResponse data = new LoginResponse();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        String token = jwtTokenProvider.generateToken(authentication);

        data.setRole(authentication.getAuthorities().iterator().next().getAuthority());
        data.setUsername(loginRequest.getUsername());
        data.setToken(token);


        return data;
    }
}
