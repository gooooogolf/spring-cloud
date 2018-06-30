package com.gooooogolf.prepaidcard.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
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
    private String cardName;
    private String cardCompany;
    private String expMonth;
    private String expYear;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Date createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Date modifiedDate;

    private String customerId;

}
