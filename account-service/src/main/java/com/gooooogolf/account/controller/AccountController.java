package com.gooooogolf.account.controller;

import com.gooooogolf.account.domain.Account;
import com.gooooogolf.account.domain.CreateAccount;
import com.gooooogolf.account.repository.AccountRepository;
import com.gooooogolf.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public Account createNewAccount(@Valid @RequestBody CreateAccount createAccount) {
        return accountService.create(createAccount);
    }

    @PutMapping
    public void saveCurrentAccount(@Valid @RequestBody CreateAccount updateAccount) {
        accountService.updateAccount(updateAccount);
    }

    @GetMapping("/{number}")
    public Account getByAccountNumber(@PathVariable String number) {
        return accountService.findByAccountNumber(number);
    }
}
