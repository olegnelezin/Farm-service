package com.example.farm.mapper;

import com.example.farm.dto.EmployeeDTO;
import com.example.farm.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
    EmployeeDTO toDTOFromEntity(Employee entity);
    Employee toEntityFromDTO(EmployeeDTO dto);

}
