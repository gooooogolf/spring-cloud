package com.gooooogolf.prepaidcard.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ValidationErrorResponse {
    private List<String> fieldErrors = new ArrayList<>();

    public void addFieldError(String error) {
        fieldErrors.add(error);
    }
}
