package com.example.farm.service;

import com.example.farm.exception.EntityAlreadyExistsException;
import com.example.farm.exception.EntityDoesNotExistException;
import com.example.farm.mapper.EmployeeMapper;
import com.example.farm.model.Employee;
import com.example.farm.model.request.DeleteRequest;
import com.example.farm.model.request.RegisterEmployeeRequest;
import com.example.farm.repository.EmployeeRepository;
import com.example.farm.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Employee saveEmployee(RegisterEmployeeRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new EntityAlreadyExistsException("Employee already exists.");
        }
        Employee employee = employeeMapper.toEntityFromRegisterRequest(request);
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findByEmployeeId(employeeId).orElseThrow(
                () -> new EntityDoesNotExistException("Employee does not exist.")
        );
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email).orElseThrow(
                () -> new EntityDoesNotExistException("Employee does not exist.")
        );
    }

    @Transactional
    public String deleteEmployeeByEmail(DeleteRequest request) {
        Employee employee = employeeRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new EntityDoesNotExistException("Employee does not exist.")
        );
        refreshTokenRepository.deleteAllByEmployee(employee);
        employeeRepository.deleteByEmail(request.getEmail());
        return "Employee has been deleted.";
    }


}
