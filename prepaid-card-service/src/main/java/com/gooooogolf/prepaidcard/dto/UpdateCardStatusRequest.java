package com.gooooogolf.prepaidcard.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UpdateCardStatusRequest {

    @NotNull(message = "card_number is missing")
    @Pattern(regexp = "(^\\d{16}$)+", message = "invalid card_number")
    private String cardNumber;

    @NotNull(message = "cvv is missing")
    @Pattern(regexp = "(^\\d{3}$)+", message = "invalid cvv")
    private String cvv;

    @NotNull(message = "card_status is missing")
    @Size(min = 1, max = 10, message = "invalid card_status")
    private String cardStatus;
}
