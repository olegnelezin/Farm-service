package com.example.farm.controller;

import com.example.farm.model.dto.TokenDTO;
import com.example.farm.model.request.auth.LoginRequest;
import com.example.farm.model.request.auth.RefreshTokenRequest;
import com.example.farm.service.AuthService;
import com.example.farm.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh-token")
    public TokenDTO refreshToken(@RequestBody RefreshTokenRequest request) {
        return tokenService.refreshAccessToken(request);
    }
}
