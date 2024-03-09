package com.example.farm.model.request;

import com.example.farm.model.enams.EUnit;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterProductRequest {
    private String name;
    private EUnit unit;
}
