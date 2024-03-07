package com.example.farm.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private String password;
}
