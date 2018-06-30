package com.gooooogolf.prepaidcard.domain;

import java.util.Arrays;
import java.util.Optional;

public enum CardType {
    VISA("Visa"), MASTER_CARD("MasterCard");

    private String value;

    private CardType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CardType fromValue(String value) {
        Optional<CardType> matchType = Arrays.stream(CardType.values())
                .filter(cardType -> cardType.getValue().equalsIgnoreCase(value))
                .findFirst();

        return matchType.orElseThrow(() -> new IllegalArgumentException("Invalid CardType:" + value));
    }
}
