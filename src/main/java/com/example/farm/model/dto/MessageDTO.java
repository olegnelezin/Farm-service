package com.example.farm.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Data
@AllArgsConstructor
public class MessageDTO {
    @JsonProperty("message")
    String message;

    public MessageDTO() {
        message = null;
    }
}
