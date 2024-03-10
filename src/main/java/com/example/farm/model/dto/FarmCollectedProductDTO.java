package com.example.farm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FarmCollectedProductDTO {
    String productName;
    String unit;
    Long count;
    @Override
    public String toString() {
        return "name: " + productName + "\n" +
                "unit: " + unit + "\n" +
                "count: " + count + "\n";
    }
}
