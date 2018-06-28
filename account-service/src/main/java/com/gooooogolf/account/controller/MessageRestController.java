package com.gooooogolf.account.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class MessageRestController {

    @Value("${message:Hello default}")
    private String message;

    @Value("${server.port}")
    private String portNumber;

    @RequestMapping("/message")
    String getMessage() {
        return String.format("%s with Port Number %s", message, portNumber);
    }

}
