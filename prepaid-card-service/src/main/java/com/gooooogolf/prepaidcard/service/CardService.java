package com.gooooogolf.prepaidcard.service;

import com.gooooogolf.prepaidcard.domain.Card;
import com.gooooogolf.prepaidcard.domain.CardStatus;
import com.gooooogolf.prepaidcard.domain.CardType;
import com.gooooogolf.prepaidcard.dto.CardResponse;
import com.gooooogolf.prepaidcard.dto.CreateCardRequest;
import com.gooooogolf.prepaidcard.dto.UpdateCardStatusRequest;
import com.gooooogolf.prepaidcard.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Slf4j
public class CardService {

    @Autowired
    private CardRepository repository;

    public CardResponse createCard(CreateCardRequest createCard) {
        Card existing = repository.findByCardNumber(createCard.getCardNumber());
        Assert.isNull(existing, "card already exists: " + createCard.getCardNumber());

        Card card = new Card();
        card.setCardId(createCard.getCardId());
        card.setCardNumber(createCard.getCardNumber());
        card.setCvv(createCard.getCcv());
        card.setExpMonth(createCard.getExpMonth());
        card.setExpYear(createCard.getExpYear());
        card.setCardType(CardType.fromValue(createCard.getCardType()));
        repository.save(card);

        log.info("new card has been created: {}", card.getCardNumber());

        return convertToCardResponse(card);
    }

    public void updateCardStatus(String cardNumber, UpdateCardStatusRequest cardStatus) {
        Card card = repository.findByCardNumber(cardNumber);
        Assert.notNull(card, "can't find card with number " + cardNumber);
        Assert.isTrue(card.getCvv().equals(cardStatus.getCcv()), "ccv is not match");

        card.setCardStatus(CardStatus.fromName(cardStatus.getCardStatus()));
        saveChanges(cardNumber, card);

        log.debug("card {} status ({}) has been updated", cardNumber, cardStatus);
    }

    public void saveChanges(String cardNumber, Card update) {
        repository.save(update);

        log.debug("card {} changes has been saved", cardNumber);
    }

    public CardResponse findByCardNumber(String cardNumber) {
        return convertToCardResponse(repository.findByCardNumber(cardNumber));
    }

    private CardResponse convertToCardResponse(Card card) {
        CardResponse cardResponse = new ModelMapper().map(card, CardResponse.class);

        return cardResponse;
    }
}
