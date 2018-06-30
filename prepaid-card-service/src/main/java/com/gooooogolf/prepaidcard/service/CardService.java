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

import java.util.List;
import java.util.stream.Collectors;

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
        card.setCvv(createCard.getCvv());
        card.setExpMonth(createCard.getExpMonth());
        card.setExpYear(createCard.getExpYear());
        card.setCardType(CardType.fromValue(createCard.getCardType()));

        log.info("new card has been created: {}", card.getCardNumber());

        return convertToCardResponse(repository.save(card));
    }

    public void updateCardStatus(String cardNumber, UpdateCardStatusRequest cardStatus) {
        Card card = repository.findByCardNumber(cardNumber);
        Assert.notNull(card, "can't find card with number " + cardNumber);
        Assert.isTrue(card.getCvv().equals(cardStatus.getCvv()), "cvv is not match");
        Assert.isTrue(card.getCustomerId().equals(cardStatus.getCustomerId()), "customer is not match");

        card.setCardStatus(CardStatus.fromName(cardStatus.getCardStatus()));
        saveChanges(cardNumber, card);

        log.debug("card {} status is {}", cardNumber, cardStatus.getCardStatus());
    }

    public void saveChanges(String cardNumber, Card update) {
        repository.save(update);

        log.debug("card {} changes has been saved", cardNumber);
    }

    public CardResponse findByCardNumber(String cardNumber) {
        return convertToCardResponse(repository.findByCardNumber(cardNumber));
    }

    public List<CardResponse> findByCustomerId(String customerId) {
        return repository.findByCustomerId(customerId).stream()
                .map(item -> convertToCardResponse(item))
                .collect(Collectors.toList());
    }

    private CardResponse convertToCardResponse(Card card) {
        CardResponse cardResponse = new ModelMapper().map(card, CardResponse.class);

        return cardResponse;
    }
}
