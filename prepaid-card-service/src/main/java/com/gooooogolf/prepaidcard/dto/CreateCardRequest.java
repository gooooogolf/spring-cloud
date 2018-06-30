package com.gooooogolf.prepaidcard.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateCardRequest {

    @NotNull(message = "card_id is missing")
    @Size(min = 1, message = "invalid card_id")
    private String cardId;

    @NotNull(message = "card_number is missing")
    @Pattern(regexp = "(^\\d{16}$)+", message = "invalid card_number")
    private String cardNumber;

    @NotNull(message = "cvv is missing")
    @Pattern(regexp = "(^\\d{3}$)+", message = "invalid cvv")
    private String cvv;

    @NotNull(message = "exp_month is missing")
    @Pattern(regexp = "(^\\d{2}$)+", message = "invalid exp_month")
    private String expMonth;

    @NotNull(message = "exp_year is missing")
    @Pattern(regexp = "(^\\d{4}$)+", message = "invalid exp_year")
    private String expYear;

    @NotNull(message = "card_type is missing")
    @Size(min = 1, max = 30, message = "invalid card_type")
    private String cardType;
}
