package com.gooooogolf.prepaidcard.service;


import com.gooooogolf.prepaidcard.domain.Card;
import com.gooooogolf.prepaidcard.domain.CardStatus;
import com.gooooogolf.prepaidcard.domain.CardType;
import com.gooooogolf.prepaidcard.dto.CardResponse;
import com.gooooogolf.prepaidcard.dto.CreateCardRequest;
import com.gooooogolf.prepaidcard.dto.UpdateCardStatusRequest;
import com.gooooogolf.prepaidcard.repository.CardRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static com.gooooogolf.prepaidcard.domain.CardStatus.ACTIVE;
import static com.gooooogolf.prepaidcard.domain.CardStatus.INACTIVE;
import static com.gooooogolf.prepaidcard.domain.CardType.VIRTUAL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CardServiceTest {

    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    private Long id = 1L;
    private CardType cardType = VIRTUAL;
    private CardStatus cardStatus = INACTIVE;
    private String cardId = "942844931049980509";
    private String cardNumber = "5541710500064352";
    private String cvv = "999";
    private String expMonth = "12";
    private String expYear = "2020";
    private Date modifiedDate = new Date();


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_createCardSuccess() {
        Card cardMock = cardMock();
        Mockito.when(cardRepository.findByCardNumber(Mockito.anyString())).thenReturn(null);
        Mockito.when(cardRepository.save(Mockito.any(Card.class))).thenReturn(cardMock);

        CardResponse cardResponse = cardService.createCard(createCardRequestMock());

        Assert.assertThat(cardResponse.getCardId(), is(equalTo(cardMock.getCardId())));
        Assert.assertThat(cardResponse.getCardNumber(), is(equalTo(cardMock.getCardNumber())));
        Assert.assertThat(cardResponse.getCvv(), is(equalTo(cardMock.getCvv())));
        Assert.assertThat(cardResponse.getCardType(), is(equalTo(cardMock.getCardType().name())));
        Assert.assertThat(cardResponse.getExpYear(), is(equalTo(cardMock.getExpYear())));
        Assert.assertThat(cardResponse.getExpMonth(), is(equalTo(cardMock.getExpMonth())));
        Assert.assertThat(cardResponse.getModifiedDate(), is(equalTo(cardMock.getModifiedDate())));

        Mockito.verify(cardRepository, Mockito.times(1)).findByCardNumber(Mockito.anyString());
        Mockito.verify(cardRepository, Mockito.times(1)).save(Mockito.any(Card.class));
    }

    @Test
    public void test_createCardWhenCardAlreadyExistShouldFail() {
        Card cardMock = cardMock();
        Mockito.when(cardRepository.findByCardNumber(Mockito.anyString())).thenReturn(cardMock);

        try {
            cardService.createCard(createCardRequestMock());
        } catch (Exception e) {
            Assert.assertEquals(String.format("card already exists: %s", cardMock.getCardNumber()), e.getMessage());
        }

        Mockito.verify(cardRepository, Mockito.times(1)).findByCardNumber(Mockito.anyString());
        Mockito.verify(cardRepository, Mockito.never()).save(Mockito.any(Card.class));
    }

    @Test
    public void test_updateCardSuccess() {
        Card cardMock = cardMock();
        Mockito.when(cardRepository.findByCardNumber(Mockito.anyString())).thenReturn(cardMock);
        Mockito.when(cardRepository.save(Mockito.any(Card.class))).thenReturn(cardMock);

        cardService.updateCardStatus(cardMock.getCardNumber(), updateCardStatusRequestMock(ACTIVE));

        Mockito.verify(cardRepository, Mockito.times(1)).findByCardNumber(Mockito.anyString());
        Mockito.verify(cardRepository, Mockito.times(1)).save(Mockito.any(Card.class));

    }

    @Test
    public void test_updateCardWhenCardNotFoundShouldFail() {
        UpdateCardStatusRequest updateCardStatusRequest = updateCardStatusRequestMock(ACTIVE);
        Mockito.when(cardRepository.findByCardNumber(Mockito.anyString())).thenReturn(null);

        try {
            cardService.updateCardStatus(updateCardStatusRequest.getCardNumber(), updateCardStatusRequest);
        } catch (Exception e) {
            Assert.assertEquals(String.format("can't find card with number %s", updateCardStatusRequest.getCardNumber()), e.getMessage());
        }

        Mockito.verify(cardRepository, Mockito.times(1)).findByCardNumber(Mockito.anyString());
        Mockito.verify(cardRepository, Mockito.never()).save(Mockito.any(Card.class));
    }

    @Test
    public void test_updateCardWhenCvvNotMatchShouldFail() {
        Card cardMock = cardMock();
        UpdateCardStatusRequest updateCardStatusRequest = updateCardStatusRequestMock(ACTIVE);
        updateCardStatusRequest.setCvv("000");

        Mockito.when(cardRepository.findByCardNumber(Mockito.anyString())).thenReturn(cardMock);

        try {
            cardService.updateCardStatus(updateCardStatusRequest.getCardNumber(), updateCardStatusRequest);
        } catch (Exception e) {
            Assert.assertEquals("cvv is not match", e.getMessage());
        }

        Mockito.verify(cardRepository, Mockito.times(1)).findByCardNumber(Mockito.anyString());
        Mockito.verify(cardRepository, Mockito.never()).save(Mockito.any(Card.class));
    }

    @Test
    public void test_findCardSuccess() {
        Card cardMock = cardMock();
        Mockito.when(cardRepository.findByCardNumber(Mockito.anyString())).thenReturn(cardMock);

        CardResponse cardResponse = cardService.findByCardNumber(cardMock.getCardNumber());

        Assert.assertThat(cardResponse.getCardId(), is(equalTo(cardMock.getCardId())));
        Assert.assertThat(cardResponse.getCardNumber(), is(equalTo(cardMock.getCardNumber())));
        Assert.assertThat(cardResponse.getCvv(), is(equalTo(cardMock.getCvv())));
        Assert.assertThat(cardResponse.getCardType(), is(equalTo(cardMock.getCardType().name())));
        Assert.assertThat(cardResponse.getExpYear(), is(equalTo(cardMock.getExpYear())));
        Assert.assertThat(cardResponse.getExpMonth(), is(equalTo(cardMock.getExpMonth())));
        Assert.assertThat(cardResponse.getModifiedDate(), is(equalTo(cardMock.getModifiedDate())));

        Mockito.verify(cardRepository, Mockito.times(1)).findByCardNumber(Mockito.anyString());
    }

    private Card cardMock() {
        Card card = new Card();
        card.setId(id);
        card.setCardId(cardId);
        card.setCardNumber(cardNumber);
        card.setCvv(cvv);
        card.setCardType(cardType);
        card.setCardStatus(cardStatus);
        card.setExpYear(expYear);
        card.setExpMonth(expMonth);
        card.setCreatedDate(modifiedDate);
        card.setModifiedDate(modifiedDate);

        return card;
    }

    private CreateCardRequest createCardRequestMock() {
        CreateCardRequest createCardRequest = new CreateCardRequest();
        createCardRequest.setCardId(cardId);
        createCardRequest.setCardNumber(cardNumber);
        createCardRequest.setCvv(cvv);
        createCardRequest.setCardType(cardType.getValue());
        createCardRequest.setExpYear(expYear);
        createCardRequest.setExpMonth(expMonth);

        return createCardRequest;
    }

    private UpdateCardStatusRequest updateCardStatusRequestMock(CardStatus cardStatus) {
        UpdateCardStatusRequest updateCardStatusRequest = new UpdateCardStatusRequest();
        updateCardStatusRequest.setCardNumber(cardNumber);
        updateCardStatusRequest.setCvv(cvv);
        updateCardStatusRequest.setCardStatus(cardStatus.getName());

        return updateCardStatusRequest;
    }

}
