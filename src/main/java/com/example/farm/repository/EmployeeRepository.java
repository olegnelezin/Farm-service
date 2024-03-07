package com.example.farm.repository;

import com.example.farm.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    Employee findByEmployeeId(Long id);

    boolean existsByEmail(String email);
    boolean existsByEmployeeId(Long id);

}
