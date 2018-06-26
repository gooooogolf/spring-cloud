package com.gooooogolf.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class AccountApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApiGatewayApplication.class, args);
    }
}
