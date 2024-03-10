package com.example.farm.repository;

import com.example.farm.model.Employee;
import com.example.farm.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    @Modifying
    @Query(value = """
                    INSERT INTO refresh_token(token, valid_till, employee_id)
                    VALUES(:refreshToken, :expireDate, :employeeId)""", nativeQuery = true)
    void saveNewRefreshToken(@Param("refreshToken") UUID refreshToken,
                             @Param("expireDate") Instant expireDate,
                             @Param("employeeId") Long employeeId);

    void deleteAllByEmployee(Employee employee);
}
