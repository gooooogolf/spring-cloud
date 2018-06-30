package com.gooooogolf.prepaidcard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooooogolf.prepaidcard.domain.CardStatus;
import com.gooooogolf.prepaidcard.domain.CardType;
import com.gooooogolf.prepaidcard.dto.CardResponse;
import com.gooooogolf.prepaidcard.dto.CreateCardRequest;
import com.gooooogolf.prepaidcard.dto.UpdateCardStatusRequest;
import com.gooooogolf.prepaidcard.service.CardService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static com.gooooogolf.prepaidcard.domain.CardStatus.ACTIVE;
import static com.gooooogolf.prepaidcard.domain.CardStatus.INACTIVE;
import static com.gooooogolf.prepaidcard.domain.CardType.VIRTUAL;
import static org.hamcrest.Matchers.hasSize;

public class CardControllerTest {

    @InjectMocks
    private CardController cardController;
    @Mock
    private CardService cardService;

    private MockMvc mockMvc;
    private String CARDS_PATH = "/cards";
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
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(cardController)
                .build();
    }

    @Test
    public void test_createCardSuccess() throws Exception {
        CardResponse response = cardResponseMock();
        Mockito.when(cardService.createCard(Mockito.any(CreateCardRequest.class))).thenReturn(response);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(CARDS_PATH)
                .content(new ObjectMapper().writeValueAsString(createCardRequestMock()))
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.card_id").value(response.getCardId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.card_number").value(response.getCardNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.card_type").value(response.getCardType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cvv").value(response.getCvv()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exp_year").value(response.getExpYear()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exp_month").value(response.getExpMonth()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modified_date").value(response.getModifiedDate()))
        ;

        Mockito.verify(cardService, Mockito.times(1)).createCard(Mockito.any(CreateCardRequest.class));
    }

    @Test
    public void test_updateCardStatusSuccess() throws Exception {
        CardResponse response = cardResponseMock();
        Mockito.doNothing().when(cardService).updateCardStatus(Mockito.anyString(), Mockito.any(UpdateCardStatusRequest.class));

        UpdateCardStatusRequest updateCardStatusRequest = updateCardStatusRequestMock(ACTIVE);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(CARDS_PATH + "/" + updateCardStatusRequest.getCardNumber())
                .content(new ObjectMapper().writeValueAsString(updateCardStatusRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(response.getCardNumber()))
        ;

        Mockito.verify(cardService, Mockito.times(1)).updateCardStatus(Mockito.anyString(), Mockito.any(UpdateCardStatusRequest.class));
    }

    @Test
    public void test_findCardSuccess() throws Exception {
        CardResponse response = cardResponseMock();
        Mockito.when(cardService.findByCardNumber(Mockito.anyString())).thenReturn(response);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(CARDS_PATH + "/" + response.getCardNumber())
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.card_id").value(response.getCardId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.card_number").value(response.getCardNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.card_type").value(response.getCardType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cvv").value(response.getCvv()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exp_year").value(response.getExpYear()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exp_month").value(response.getExpMonth()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modified_date").value(response.getModifiedDate()))
        ;

        Mockito.verify(cardService, Mockito.times(1)).findByCardNumber(Mockito.anyString());
    }

    @Test
    public void test_createCardWhenMissingCardNumberShouldFail() throws Exception {
        CardResponse response = cardResponseMock();
        Mockito.when(cardService.createCard(Mockito.any(CreateCardRequest.class))).thenReturn(response);

        CreateCardRequest createCardRequest = createCardRequestMock();
        createCardRequest.setCardNumber(null);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(CARDS_PATH)
                .content(new ObjectMapper().writeValueAsString(createCardRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.field_errors", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.field_errors[0]").value("card_number is missing"))
        ;

        Mockito.verify(cardService, Mockito.never()).createCard(Mockito.any(CreateCardRequest.class));
    }

    @Test
    public void test_createCardWhenInvalidCardNumberShouldFail() throws Exception {
        CardResponse response = cardResponseMock();
        Mockito.when(cardService.createCard(Mockito.any(CreateCardRequest.class))).thenReturn(response);

        CreateCardRequest createCardRequest = createCardRequestMock();
        createCardRequest.setCardNumber("123456789");
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(CARDS_PATH)
                .content(new ObjectMapper().writeValueAsString(createCardRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.field_errors", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.field_errors[0]").value("invalid card_number"))
        ;

        Mockito.verify(cardService, Mockito.never()).createCard(Mockito.any(CreateCardRequest.class));
    }

    @Test
    public void test_updateCardStatusWhenMissingCardStatusShouldFail() throws Exception {
        CardResponse response = cardResponseMock();
        Mockito.doNothing().when(cardService).updateCardStatus(Mockito.anyString(), Mockito.any(UpdateCardStatusRequest.class));

        UpdateCardStatusRequest updateCardStatusRequest = updateCardStatusRequestMock(ACTIVE);
        updateCardStatusRequest.setCardStatus(null);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(CARDS_PATH + "/" + updateCardStatusRequest.getCardNumber())
                .content(new ObjectMapper().writeValueAsString(updateCardStatusRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.field_errors", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.field_errors[0]").value("card_status is missing"))
        ;

        Mockito.verify(cardService, Mockito.never()).updateCardStatus(Mockito.anyString(), Mockito.any(UpdateCardStatusRequest.class));
    }

    private CardResponse cardResponseMock() {
        CardResponse cardResponse = new CardResponse();
        cardResponse.setCardId(cardId);
        cardResponse.setCardNumber(cardNumber);
        cardResponse.setCvv(cvv);
        cardResponse.setCardType(cardType.name());
        cardResponse.setExpYear(expYear);
        cardResponse.setExpMonth(expMonth);
        cardResponse.setModifiedDate(modifiedDate);

        return cardResponse;
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
