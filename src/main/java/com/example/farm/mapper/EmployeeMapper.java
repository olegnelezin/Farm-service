package com.example.farm.mapper;

import com.example.farm.model.Employee;
import com.example.farm.model.dto.EmployeeDTO;
import com.example.farm.model.request.admin.RegisterEmployeeRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class EmployeeMapper {
    private final PasswordEncoder passwordEncoder;

    public List<EmployeeDTO> toDTOFromEntity(List<Employee> employeeList) {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for (Employee employee: employeeList) {
            employeeDTOList.add(toDTOFromEntity(employee));
        }
        return employeeDTOList;
    }

    public Employee toEntityFromRegisterRequest(RegisterEmployeeRequest request) {
        return new Employee(
                request.getFirstName(),
                request.getLastName(),
                request.getPatronymic(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
                );
    }

    public EmployeeDTO toDTOFromEntity(Employee employee) {
        return new EmployeeDTO(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getPatronymic(),
                employee.getEmail(),
                employee.getPassword(),
                employee.getRole().name()
        );
    }
}
