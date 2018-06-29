package com.gooooogolf.prepaidcard.service;

import com.gooooogolf.prepaidcard.domain.Card;

public interface CardService {

    void createCard(Card card);

    void saveChanges(String cardNumber, Card card);

    Card findByCardNumber(String cardNumber);

}
