package com.example.farm.service;

import com.example.farm.dto.EmployeeDTO;
import com.example.farm.mapper.EmployeeMapper;
import com.example.farm.model.Employee;
import com.example.farm.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeDTO getEmployeeByEmail(String email) throws Exception {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(
                () -> new Exception("Can not find user with this Email")
        );
        return employeeMapper.toDTOFromEntity(employee);
    }

    public EmployeeDTO getEmployeeById(Long id) {
        return employeeMapper.toDTOFromEntity(employeeRepository.findByEmployeeId(id));
    }

}
