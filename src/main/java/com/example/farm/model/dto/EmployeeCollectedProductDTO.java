package com.example.farm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class EmployeeCollectedProductDTO {
    String email;
    String productName;
    String unit;
    Long count;
}
