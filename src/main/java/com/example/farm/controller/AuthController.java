package com.example.farm.controller;

import com.example.farm.model.dto.MessageDTO;
import com.example.farm.model.dto.TokenDTO;
import com.example.farm.model.request.LoginRequest;
import com.example.farm.model.request.RegisterRequest;
import com.example.farm.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequest request) {
        TokenDTO tokens = authService.login(request);
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }
}