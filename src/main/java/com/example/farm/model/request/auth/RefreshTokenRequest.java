package com.example.farm.model.request.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshTokenRequest {
    private String refreshToken;
}
