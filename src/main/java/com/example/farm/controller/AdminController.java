package com.example.farm.controller;

import com.example.farm.model.dto.TokenDTO;
import com.example.farm.model.request.RegisterRequest;
import com.example.farm.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(@RequestBody RegisterRequest request) {
        TokenDTO tokens = authService.register(request);
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }
}
