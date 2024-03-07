package com.example.farm.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.farm.model.dto.EmployeeDTO;
import com.example.farm.model.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TokenService {
    @Value("${jwt.token.key}")
    private String jwtSecret;

    @Value("${jwt.token.time}")
    private Long ExpireTimeInMs;
    public static final String TOKEN_PREFIX = "Bearer ";
    private final UserDetailsService userDetailsService;

    public void authenticate(String authHeader) {
        DecodedJWT decodedJWT = decodeJWT(authHeader);
        Map<String, Claim> claims = decodedJWT.getClaims();
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.get("email").asString());
        //checkIfTokenIsExpired(claims.get("exp").asDate());
        setAuthToken(userDetails);
    }

    private DecodedJWT decodeJWT(String authHeader) {
        String token = authHeader;
        if (authHeader.startsWith(TOKEN_PREFIX)) {
            token = authHeader.substring(TOKEN_PREFIX.length());
        }
        var algorithm = getAlgorithm();
        return JWT.require(algorithm)
                .build()
                .verify(token);
    }
    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtSecret.getBytes());
    }

    private void setAuthToken(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private String generateAccessToken(EmployeeDTO employee) {
        return JWT.create()
                .withSubject(employee.getEmployeeId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + ExpireTimeInMs))
                .withClaim("role", employee.getRole())
                .withClaim("email", employee.getEmail())
                .withClaim("firstName", employee.getFirstName())
                .withClaim("lastName", employee.getLastName())
                .withClaim("patronymic", employee.getPatronymic())
                .sign(getAlgorithm());
    }

    private UUID generateRefreshToken(EmployeeDTO employee) {
        UUID refreshToken = UUID.randomUUID();
        /*
        refreshTokenRepository.saveNewRefreshToken(
                refreshToken,
                Instant.now().plusSeconds(fromDaysToSeconds(refreshTokenExpireTimeInDays)),
                employee.getId());
        */
        return refreshToken;
    }

    @Transactional
    public TokenDTO createTokens(EmployeeDTO employee) {
        return new TokenDTO(generateAccessToken(employee), generateRefreshToken(employee).toString());
    }
}
