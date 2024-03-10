package com.example.farm.service;

import com.example.farm.exception.EntityAlreadyExistsException;
import com.example.farm.exception.EntityDoesNotExistException;
import com.example.farm.model.Employee;
import com.example.farm.model.EmployeeMark;
import com.example.farm.model.request.SetMarkRequest;
import com.example.farm.repository.EmployeeMarkRepository;
import com.example.farm.util.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@AllArgsConstructor
@Service
public class EmployeeMarkService {
    private final EmployeeMarkRepository employeeMarkRepository;
    private final EmployeeService employeeService;

    @Transactional
    public String saveEmployeeMark(SetMarkRequest request) {
        Date today = DateUtils.getCurrentDay();
        Employee employee = employeeService.getEmployeeByEmail(request.getEmail());
        if (employeeMarkRepository.existsByEmployeeAndDate(employee, today)) {
            throw new EntityAlreadyExistsException("Mark already exists.");
        }
        EmployeeMark employeeMark = new EmployeeMark(today, employee, request.getMark());
        employeeMarkRepository.save(employeeMark);
        return "Mark has been added.";
    }

    public EmployeeMark getEmployeeMark(String email) {
        Date today = DateUtils.getCurrentDay();
        Employee employee = employeeService.getEmployeeByEmail(email);
        return employeeMarkRepository.getEmployeeMarkByEmployeeAndDate(employee, today).orElseThrow(
                () -> new EntityDoesNotExistException("Mark does not exist.")
        );
    }
}
