package com.gooooogolf.customer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateCustomerRequest {

    @NotNull(message = "citizen_id is missing")
    @Pattern(regexp = "(^\\d{16}$)+", message = "invalid citizen_id")
    private String citizenId;

    @NotNull(message = "first_name is missing")
    @Size(min = 1, max = 50, message = "invalid first_name")
    private String firstName;

    @NotNull(message = "last_name is missing")
    @Size(min = 1, max = 50, message = "invalid last_name")
    private String lastName;

    @Size(max = 50, message = "invalid middle_name")
    private String middleName;

    @Size(min = 2, max = 50, message = "invalid title")
    private String title;

    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "invalid email")
    private String email;

    @Pattern(regexp = "(^\\d{10,15}$)+", message = "mobile")
    private String mobile;
}
