package com.gooooogolf.prepaidcard.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UpdateCardStatusRequest {
    private String cardNumber;
    private String cvv;
    private String cardStatus;
}
