package com.example.farm.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.farm.mapper.EmployeeMapper;
import com.example.farm.model.dto.EmployeeDTO;
import com.example.farm.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import static com.example.farm.filter.AuthFilter.AUTH_HEADER;


@RequiredArgsConstructor
@Component
public class TokenFilter {
    @Value("${jwt.token.key}")
    private String jwtSecret;

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;
    private final ObjectMapper objectMapper;

    public void authenticate(String authHeader) {
        var decodedJWT = decodeJWT(authHeader);
        EmployeeDTO employee = employeeMapper.toDTOFromEntity(
                employeeService.getEmployeeById(Long.parseLong(decodedJWT.getSubject())));
        setAuthenticationToken(employee, decodedJWT);
    }

    public DecodedJWT decodeJWT(String authHeader) {
        String token = authHeader;
        if (authHeader.contains(AUTH_HEADER)) {
            token = authHeader.substring(AUTH_HEADER.length());
        }
        var algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
        return JWT.require(algorithm).build().verify(token);
    }

    private void setAuthenticationToken(EmployeeDTO employee, DecodedJWT decodedJWT) {
        String username = employee.getEmail();
        String password = employee.getHashPassword();
        String role = decodedJWT.getClaim("role").asString();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

        var authenticationToken = new UsernamePasswordAuthenticationToken(username, password, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public void sendErrorMessage(HttpServletResponse response, String message) throws IOException {
        response.setStatus(403);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(objectMapper.writeValueAsString(message));
        out.flush();
    }
}
