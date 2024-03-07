package com.example.farm.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class MessageDTO {
    @JsonProperty("message")
    String message;
    public MessageDTO() {
        this.message = null;
    }
}
