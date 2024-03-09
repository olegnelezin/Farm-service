package com.example.farm.model.dto;

import com.example.farm.model.enams.EUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {
    @JsonIgnore
    private Long productId;
    private String name;
    private String unit;
}
