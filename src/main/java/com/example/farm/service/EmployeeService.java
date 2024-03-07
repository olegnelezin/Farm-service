package com.example.farm.service;

import com.example.farm.exception.EmployeeAlreadyExistsException;
import com.example.farm.exception.EmployeeDoesNotExistException;
import com.example.farm.mapper.EmployeeMapper;
import com.example.farm.model.Employee;
import com.example.farm.model.dto.EmployeeDTO;
import com.example.farm.model.request.RegisterRequest;
import com.example.farm.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeDTO register(RegisterRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new EmployeeAlreadyExistsException("Employee already exists.");
        }
        Employee employee = employeeMapper.toEntityFromRegisterRequest(request);
        employeeRepository.save(employee);
        return employeeMapper.toDTOFromEntity(employee);
    }

    public EmployeeDTO getEmployeeById(Long employeeId) {
        if (!employeeRepository.existsByEmployeeId(employeeId))  {
            throw new EmployeeDoesNotExistException("Employee does not exist.");
        }
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        return employeeMapper.toDTOFromEntity(employee);
    }

    public EmployeeDTO getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(
                () -> new EmployeeDoesNotExistException("Employee does not exist.")
        );
        return employeeMapper.toDTOFromEntity(employee);

    }
}
