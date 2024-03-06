package com.example.farm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@AllArgsConstructor
@Getter
@Setter
public class DefaultMessageDTO {
    @JsonProperty("message")
    private String message;
}
