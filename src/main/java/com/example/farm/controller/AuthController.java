package com.example.farm.controller;

import com.example.farm.model.dto.MessageDTO;
import com.example.farm.model.dto.TokenDTO;
import com.example.farm.model.request.auth.LoginRequest;
import com.example.farm.model.request.auth.RefreshTokenRequest;
import com.example.farm.service.AuthService;
import com.example.farm.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/logout")
    public MessageDTO logout(HttpServletRequest request) {
        tokenService.invalidateToken(request);
        return new MessageDTO("You have been successfully logout.");
    }
}
