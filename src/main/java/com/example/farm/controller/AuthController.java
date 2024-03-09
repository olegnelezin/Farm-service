package com.example.farm.controller;

import com.example.farm.model.dto.TokenDTO;
import com.example.farm.model.request.LoginRequest;
import com.example.farm.model.request.RefreshTokenRequest;
import com.example.farm.service.AuthService;
import com.example.farm.service.TokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequest request) {
        TokenDTO tokens = authService.login(request);
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDTO> refreshToken(@RequestBody RefreshTokenRequest request) {
        TokenDTO tokens = tokenService.refreshAccessToken(request);
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }
}
