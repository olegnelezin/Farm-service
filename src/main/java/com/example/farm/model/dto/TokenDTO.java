package com.example.farm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
}
