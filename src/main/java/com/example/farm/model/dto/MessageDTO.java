package com.example.farm.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
