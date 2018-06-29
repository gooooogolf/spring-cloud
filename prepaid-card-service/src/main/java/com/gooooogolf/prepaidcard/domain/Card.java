package com.gooooogolf.prepaidcard.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Card {

    @Id
    @GeneratedValue
    private Long id;
    private CardType cardType;
    private CardStatus cardStatus;
    private String cardId;
    private String cardNumber;
    private String cvv;
    private String expMonth;
    private String expYear;
    private Date createdDate;
    private Date modifiedDate;

}
