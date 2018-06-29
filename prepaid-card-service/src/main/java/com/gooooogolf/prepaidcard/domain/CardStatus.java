package com.gooooogolf.prepaidcard.domain;

import java.util.Arrays;
import java.util.Optional;

public enum CardStatus {
    INACTIVE(0, "inactive"), ACTIVE(1, "active"), STOPPED(-1, "stopped");

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private CardStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static CardStatus fromCode(int code) {
        Optional<CardStatus> matchType = Arrays.stream(CardStatus.values())
                .filter(cardStatus -> cardStatus.getCode() == code)
                .findFirst();

        return matchType.orElseThrow(() -> new IllegalArgumentException("Invalid CardStatus:" + code));
    }

    public static CardStatus fromName(String name) {
        Optional<CardStatus> matchType = Arrays.stream(CardStatus.values())
                .filter(cardStatus -> cardStatus.getName().equalsIgnoreCase(name))
                .findFirst();

        return matchType.orElseThrow(() -> new IllegalArgumentException("Invalid CardStatus:" + name));
    }
}
