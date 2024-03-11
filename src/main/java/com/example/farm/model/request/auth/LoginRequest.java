package com.example.farm.model.request.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {
    private String email;
    private String password;
}
