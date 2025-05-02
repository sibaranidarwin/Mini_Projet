package com.example.mini_project.controller;

import com.example.mini_project.dto.auth.LoginResponse;
import com.example.mini_project.dto.auth.LoginrRequest;
import com.example.mini_project.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

   @Autowired
   AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginrRequest request) {
        return authService.login(request);
    }
}

