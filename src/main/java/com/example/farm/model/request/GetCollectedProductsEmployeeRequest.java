package com.example.farm.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetCollectedProductsEmployeeRequest {
    private String period; // "day" or "month"
    private int periodNumber; // day: 1 - 31; month: 1 - 12
    private String email;
}
