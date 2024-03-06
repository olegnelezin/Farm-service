package com.example.farm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;

@Data
@AllArgsConstructor
public class EmployeeDTO {
    @JsonIgnore
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String passwordHash;
    private Set<RoleDTO> roles;
}
