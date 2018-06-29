package com.gooooogolf.prepaidcard.controller;

import com.gooooogolf.prepaidcard.dto.CreateCardRequest;
import com.gooooogolf.prepaidcard.dto.UpdateCardStatusRequest;
import com.gooooogolf.prepaidcard.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity createCard(@Valid @RequestBody CreateCardRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.createCard(request));
    }

    @GetMapping(value = "/{cardNumber}")
    public ResponseEntity findByCardNumber(@PathVariable String cardNumber) {
        return ResponseEntity.ok(cardService.findByCardNumber(cardNumber));
    }

    @PutMapping(value = "/{cardNumber}")
    public ResponseEntity updateCardStatus(@PathVariable String cardNumber, @Valid @RequestBody UpdateCardStatusRequest request) {
        cardService.updateCardStatus(cardNumber, request);

        return ResponseEntity.ok(cardNumber);
    }
}
