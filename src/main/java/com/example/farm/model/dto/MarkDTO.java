package com.example.farm.model.dto;

import com.example.farm.model.EmployeeMark;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarkDTO {
    @JsonProperty("mark")
    private short mark;

    public MarkDTO(EmployeeMark mark) {
        this.mark = mark.getMark();
    }
}
