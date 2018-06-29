package com.gooooogolf.prepaidcard.service;

import com.gooooogolf.prepaidcard.domain.Card;
import com.gooooogolf.prepaidcard.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository repository;

    @Override
    public void createCard(Card card) {
        log.info("new card has been created: " + card.getCardNumber());
    }

    @Override
    public void saveChanges(String cardNumber, Card card) {
        log.debug("card {} changes has been saved", cardNumber);
    }

    @Override
    public Card findByCardNumber(String cardNumber) {
        return null;
    }
}
