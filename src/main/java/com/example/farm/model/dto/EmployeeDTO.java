package com.example.farm.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {
    @JsonIgnore
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    @JsonIgnore
    private String hashPassword;
    @JsonIgnore
    private String role;
}
