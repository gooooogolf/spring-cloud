package com.gooooogolf.prepaidcard.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateCardRequest {
    private String cardId;
    private String cardNumber;
    private String cvv;
    private String expMonth;
    private String expYear;
    private String cardType;
}
