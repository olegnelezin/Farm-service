package com.example.farm.repository;

import com.example.farm.model.Employee;
import com.example.farm.model.EmployeeMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface EmployeeMarkRepository extends JpaRepository<EmployeeMark, Long> {
    Optional<EmployeeMark> getEmployeeMarkByEmployeeAndDate(Employee employee, Date date);
    boolean existsByEmployeeAndDate(Employee employee, Date date);
}