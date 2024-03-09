package com.example.farm.service;

import com.example.farm.exception.EntityAlreadyExistsException;
import com.example.farm.exception.EntityDoesNotExistException;
import com.example.farm.mapper.EmployeeMapper;
import com.example.farm.model.Employee;
import com.example.farm.model.dto.EmployeeDTO;
import com.example.farm.model.dto.MessageDTO;
import com.example.farm.model.request.DeleteRequest;
import com.example.farm.model.request.RegisterEmployeeRequest;
import com.example.farm.repository.EmployeeRepository;
import com.example.farm.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final RefreshTokenRepository refreshTokenRepository;

    public EmployeeDTO saveEmployee(RegisterEmployeeRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new EntityAlreadyExistsException("Employee already exists.");
        }
        Employee employee = employeeMapper.toEntityFromRegisterRequest(request);
        employeeRepository.save(employee);
        return employeeMapper.toDTOFromEntity(employee);
    }

    public EmployeeDTO getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId).orElseThrow(
                () -> new EntityDoesNotExistException("Employee does not exist.")
        );
        return employeeMapper.toDTOFromEntity(employee);
    }

    public EmployeeDTO getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(
                () -> new EntityDoesNotExistException("Employee does not exist.")
        );
        return employeeMapper.toDTOFromEntity(employee);
    }

    public MessageDTO deleteEmployeeByEmail(DeleteRequest request) {
        Employee employee = employeeRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new EntityDoesNotExistException("Employee does not exist.")
        );
        refreshTokenRepository.deleteAllByEmployee(employee);
        employeeRepository.deleteByEmail(request.getEmail());
        return new MessageDTO("Employee has been deleted.");
    }
}
