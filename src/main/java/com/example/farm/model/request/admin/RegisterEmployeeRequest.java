package com.example.farm.model.request.admin;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterEmployeeRequest {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private String password;
}
