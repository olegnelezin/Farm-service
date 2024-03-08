package com.example.farm.service;

import com.example.farm.model.dto.EmployeeDTO;
import com.example.farm.model.dto.MessageDTO;
import com.example.farm.model.dto.TokenDTO;
import com.example.farm.model.request.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class AdminService {
    private final EmployeeService employeeService;

    @Transactional
    public EmployeeDTO register(RegisterRequest request) {
        return employeeService.register(request);
    }
}
