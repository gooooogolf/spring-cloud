package com.gooooogolf.prepaidcard.repository;

import com.gooooogolf.prepaidcard.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    public List<Card> findByCardNumber(String cardNumber);
}
