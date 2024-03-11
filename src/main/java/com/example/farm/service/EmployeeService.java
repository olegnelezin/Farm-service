package com.example.farm.service;

import com.example.farm.exception.EntityAlreadyExistsException;
import com.example.farm.exception.EntityDoesNotExistException;
import com.example.farm.exception.InvalidDataException;
import com.example.farm.mapper.EmployeeMapper;
import com.example.farm.model.Employee;
import com.example.farm.model.request.admin.DeleteRequest;
import com.example.farm.model.request.admin.RegisterEmployeeRequest;
import com.example.farm.repository.EmployeeRepository;
import com.example.farm.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

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

    @Transactional
    public String editCredential(String email, String type, String credential) {
        Employee employee = getEmployeeByEmail(email);
        if (!employeeRepository.existsById(employee.getEmployeeId())) {
            throw new EntityDoesNotExistException("Employee does not exist.");
        }

        if (type.equalsIgnoreCase("email")) {
            employeeRepository.updateEmail(employee.getEmployeeId(), credential);
        } else if (type.equalsIgnoreCase("password")) {
            employeeRepository.updatePassword(employee.getEmployeeId(), passwordEncoder.encode(credential));
        } else {
            throw new InvalidDataException("Incorrect type");
        }
        return "Credential has been updated.";
    }
}
