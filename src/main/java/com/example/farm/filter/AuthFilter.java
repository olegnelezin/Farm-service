package com.example.farm.filter;


import com.example.farm.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@AllArgsConstructor
@Component
public class AuthFilter extends OncePerRequestFilter {
    public final static String AUTH_HEADER = "Bearer ";

    private final TokenFilter tokenFilter;
    private final TokenService tokenService;

    /**
     * Если у Вас не установлен Redis или Вы не хотите использовать Docker,
     * уберите проверку на валидность токена(35-39)
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(AUTH_HEADER)) {
            try {
                String token = authHeader.substring(AUTH_HEADER.length());
                if (tokenService.isTokenInvalid(token)) {
                    tokenFilter.sendErrorMessage(response, "Unauthorized");
                    return;
                }
                tokenFilter.authenticate(authHeader);
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                tokenFilter.sendErrorMessage(response, e.getMessage());
                SecurityContextHolder.clearContext();
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
