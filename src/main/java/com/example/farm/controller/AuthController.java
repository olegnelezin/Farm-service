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

    /**
     Вход в систему
     @param request Данные о работнике.
     @return Ответ HTTP-запроса со статусом 200, с данными о токенах,
     или со статусом 404, с сообщением, что работника не существует,
     или со статусом 400, с сообщением "Incorrect data input".
     */
    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    /**
     Обновить токен
     @param request Данные о токене.
     @return Ответ HTTP-запроса со статусом 200, с данными о токенах,
     или со статусом 403, с сообщением "Unauthorized",
     или со статусом 400, с сообщением "Incorrect data input".
     */
    @PostMapping("/refresh-token")
    public TokenDTO refreshToken(@RequestBody RefreshTokenRequest request) {
        return tokenService.refreshAccessToken(request);
    }

    /**
     Выйти из системы
     @param request Данные о сессии.
     @return Ответ HTTP-запроса со статусом 200, с данными о токенах,
     или со статусом 403, с сообщением "Unauthorized",
     */
    @GetMapping("/logout")
    public MessageDTO logout(HttpServletRequest request) {
        tokenService.invalidateToken(request);
        return new MessageDTO("You have been successfully logout.");
    }
}
