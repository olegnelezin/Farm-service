package com.example.farm.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.farm.dto.EmployeeDTO;
import com.example.farm.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import static com.example.farm.filter.AuthJwtFilter.AUTH_HEADER;
import static java.util.Arrays.stream;

@Component
@RequiredArgsConstructor
public class TokenFilter {
    @Value("${jwt.token.key}")
    private String jwtSecret;

    private final EmployeeService employeeService;

    public void authenticate(String authHeader) {
        var decodedJWT = decodeJWT(authHeader);
        EmployeeDTO employee = employeeService.getEmployeeById(Long.parseLong(decodedJWT.getSubject()));
        setAuthenticationToken(employee, decodedJWT);
    }

    public DecodedJWT decodeJWT(String authHeader) {
        String token = authHeader;
        if (authHeader.contains(AUTH_HEADER)) {
            token = authHeader.substring(AUTH_HEADER.length());
        }
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
        return JWT.require(algorithm).build().verify(token);
    }

    private void setAuthenticationToken(EmployeeDTO employee, DecodedJWT decodedJWT) {
        String email = employee.getEmail();
        String password = employee.getPasswordHash();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        List<SimpleGrantedAuthority> authorities = stream(roles)
                .map(SimpleGrantedAuthority::new)
                .toList();

        var authenticationToken = new UsernamePasswordAuthenticationToken(email, password, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
