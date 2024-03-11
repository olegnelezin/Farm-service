package com.example.farm.model.request.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SetPlanRequest {
    private String email;
    private String productName;
    private Long count;
}
