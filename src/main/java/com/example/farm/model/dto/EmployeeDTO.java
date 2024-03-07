package com.example.farm.model.dto;


import com.example.farm.model.enams.ERole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeDTO {
    @JsonIgnore
    Long employeeId;
    String firstName;
    String lastName;
    String patronymic;
    String email;
    String hashPassword;
    String role;
}
