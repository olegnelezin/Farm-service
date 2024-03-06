package com.example.farm.config;

import com.example.farm.mapper.EmployeeMapper;
import org.springframework.context.annotation.Bean;

public class MapperConfiguration {
    @Bean
    public EmployeeMapper employeeMapper() {
        return EmployeeMapper.INSTANCE;
    }
}
