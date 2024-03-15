package com.example.farm.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.farm.exception.EntityDoesNotExistException;
import com.example.farm.mapper.EmployeeMapper;
import com.example.farm.model.RefreshToken;
import com.example.farm.model.dto.EmployeeDTO;
import com.example.farm.model.dto.TokenDTO;
import com.example.farm.model.request.auth.RefreshTokenRequest;
import com.example.farm.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TokenService {
    @Value("${jwt.token.key}")
    private String jwtSecret;

    @Value("${jwt.token.time}")
    private Long ExpireTimeInMs;

    private static final String BLACK_LIST_KEY = "jwt-blacklist";

    private final RedisTemplate<String, String> redisTemplate;
    private final RefreshTokenRepository refreshTokenRepository;
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;



    public void invalidateToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        redisTemplate.opsForSet().add(BLACK_LIST_KEY, token);

    }

    public boolean isTokenInvalid(String token) {
        System.out.println(redisTemplate.opsForSet().isMember(BLACK_LIST_KEY, token));
        return redisTemplate.opsForSet().isMember(BLACK_LIST_KEY, token);
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtSecret.getBytes());
    }

    private void checkIfTokenIsExpired(Instant tokenExpireDate) {
        if (tokenExpireDate.isBefore(Instant.now())) {
            throw new TokenExpiredException("Old invalid token", Instant.now());
        }
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

        refreshTokenRepository.saveNewRefreshToken(
                refreshToken,
                Instant.now().plusSeconds(1200000),
                employee.getEmployeeId());

        return refreshToken;
    }

    @Transactional
    public TokenDTO createTokens(EmployeeDTO employee) {
        return new TokenDTO(generateAccessToken(employee), generateRefreshToken(employee).toString());
    }

    public TokenDTO refreshAccessToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = findRefreshTokenById(request.getRefreshToken());
        checkIfTokenIsExpired(refreshToken.getValidTill());
        return new TokenDTO(
                generateAccessToken(employeeMapper.toDTOFromEntity(employeeService.getEmployeeByEmail(refreshToken.getEmployee().getEmail()))),
                refreshToken.getToken().toString()
        );
    }

    private RefreshToken findRefreshTokenById(String refreshToken) {
        return refreshTokenRepository.findById(UUID.fromString(refreshToken)).orElseThrow(
                () -> new EntityDoesNotExistException("The token does not exist.")
        );
    }
}
