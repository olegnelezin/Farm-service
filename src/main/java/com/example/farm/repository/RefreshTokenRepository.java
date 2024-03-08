package com.example.farm.repository;

import com.example.farm.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    @Modifying
    @Query(value = """
                    INSERT INTO refresh_token(token, valid_till, employee_id)
                    VALUES(:refreshToken, :expireDate, :employeeId)""", nativeQuery = true)
    void saveNewRefreshToken(@Param("refreshToken") UUID refreshToken,
                             @Param("expireDate") Instant expireDate,
                             @Param("employeeId") Long employeeId);
}
