package com.example.farm.service;

import com.example.farm.exception.WrongLoginDataException;
import com.example.farm.mapper.EmployeeMapper;
import com.example.farm.model.dto.EmployeeDTO;
import com.example.farm.model.dto.TokenDTO;
import com.example.farm.model.request.LoginRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class AuthService {
    private final EmployeeService employeeService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeMapper employeeMapper;

    @Transactional
    public TokenDTO login(LoginRequest request) {
        if(employeeService.getEmployeeByEmail(request.getEmail()) == null){
            throw new WrongLoginDataException("Wrong email");
        }
        EmployeeDTO employee = employeeMapper.toDTOFromEntity(employeeService.getEmployeeByEmail(request.getEmail()));
        if (!passwordEncoder.matches(request.getPassword(), employee.getHashPassword())) {
            throw new WrongLoginDataException("Wrong password");
        }
        return tokenService.createTokens(employee);
    }
}
