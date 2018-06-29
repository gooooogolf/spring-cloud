package com.gooooogolf.prepaidcard.repository;

import com.gooooogolf.prepaidcard.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    public Card findByCardNumber(String cardNumber);
}
