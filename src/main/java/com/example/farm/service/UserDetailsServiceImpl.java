package com.example.farm.service;

import com.example.farm.exception.EntityDoesNotExistException;
import com.example.farm.model.Employee;
import com.example.farm.repository.EmployeeRepository;
import com.example.farm.util.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(
                () -> new EntityDoesNotExistException("Employee does not exist.")
        );
        return UserDetailsImpl.build(employee);
    }
}
