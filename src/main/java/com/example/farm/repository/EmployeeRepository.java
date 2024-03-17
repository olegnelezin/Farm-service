package com.example.farm.repository;

import com.example.farm.model.Employee;
import com.example.farm.model.enams.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByEmployeeId(Long id);
    boolean existsByEmail(String email);
    List<Employee> getAllByRole(ERole role);
    void deleteByEmail(String email);

    @Modifying
    @Query(value = "UPDATE employee e SET email = :email WHERE employee_id = :employeeId", nativeQuery = true)
    void updateEmail(Long employeeId, String email);

    @Modifying
    @Query(value = "UPDATE employee e SET password = :password WHERE employee_id = :employeeId", nativeQuery = true)
    void updatePassword(Long employeeId, String password);
}
