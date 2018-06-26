package com.gooooogolf.account.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateAccount {

    @NotNull
    @Length(min = 10, max = 10)
    private String accountNumber;

    @NotNull
    @Length(min = 3, max = 20)
    private String accountName;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
