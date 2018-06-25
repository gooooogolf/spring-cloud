package com.gooooogolf.account.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageRestController {

    @Value("${message:Hello account service default}")
    private String message;

    @RequestMapping("/message")
    String getMessage() {
        return this.message;
    }
}
