package com.example.farm.model.dto;

import com.example.farm.model.enams.ERole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {
    @JsonIgnore
    Long employeeId;
    String firstName;
    String lastName;
    String patronymic;
    String email;
    @JsonIgnore
    String hashPassword;
    @JsonIgnore
    String role;
}
