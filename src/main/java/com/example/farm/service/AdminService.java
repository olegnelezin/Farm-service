package com.example.farm.service;

import com.example.farm.model.Employee;
import com.example.farm.model.dto.EmployeeDTO;
import com.example.farm.model.dto.MessageDTO;
import com.example.farm.model.request.DeleteRequest;
import com.example.farm.model.request.RegisterEmployeeRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class AdminService {
    private final EmployeeService employeeService;

    @Transactional
    public Employee registerEmployee(RegisterEmployeeRequest request) {
        return employeeService.saveEmployee(request);
    }

    @Transactional
    public String deleteEmployee(DeleteRequest request) {
        return employeeService.deleteEmployeeByEmail(request);
    }
}
