package com.example.farm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeCollectedProductDTO {
    private String email;
    private String productName;
    private String unit;
    private Long count;
}
