package com.example.farm.service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.farm.dto.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthTokenService {
    private static final String TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.token.key}")
    private String jwtSecret;

    @Value("${jwt.token.time}")
    private long jwtExpirationMs;

    private final EmployeeService employeeService;
    private final UserDetailsService userDetailsService;

    public void auth(String authHeader) {
        DecodedJWT decodedJWT = decodeJWT(authHeader);
        Map<String, Claim> claims = decodedJWT.getClaims();
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.get("email").asString());
        //checkIfTokenIsExpired(claims.get("exp").asDate());
        setAuthToken(userDetails);
    }
    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtSecret.getBytes());
    }
    private DecodedJWT decodeJWT(String authHeader) {
        String token = authHeader;
        if (authHeader.startsWith(TOKEN_PREFIX)) {
            token = authHeader.substring(TOKEN_PREFIX.length());
        }
        Algorithm algorithm = getAlgorithm();
        return JWT.require(algorithm)
                .build()
                .verify(token);
    }

    private void setAuthToken(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private String generateToken(EmployeeDTO employee) {
        return JWT.create()
                .withSubject(employee.getEmployeeId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .withClaim("roles", employee.getRoles().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
                )
                .withClaim("email", employee.getEmail())
                .withClaim("firstName", employee.getFirstName().concat(" ").concat(employee.getLastName()))
                .sign(getAlgorithm());
    }
}
