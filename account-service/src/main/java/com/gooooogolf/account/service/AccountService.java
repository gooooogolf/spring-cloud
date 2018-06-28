package com.gooooogolf.account.service;

import com.gooooogolf.account.domain.Account;
import com.gooooogolf.account.domain.CreateAccount;
import com.gooooogolf.account.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AccountService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountRepository repository;

    public Account create(CreateAccount createAccount) {
        Account existing = repository.findByAccountNumber(createAccount.getAccountNumber());
        Assert.isNull(existing, "account already exists: " + createAccount.getAccountNumber());

        Account account = new Account();
        account.setAccountNumber(createAccount.getAccountNumber());
        account.setAccountName(createAccount.getAccountName());

        repository.save(account);

        log.info("new account has been created: " + account.getAccountName());

        return account;
    }

    public Account findByAccountNumber(String accountNumber) {
        Assert.hasLength(accountNumber);
        return repository.findByAccountNumber(accountNumber);
    }

    public void updateAccount(CreateAccount updateAccount) {
        Account account = repository.findByAccountNumber(updateAccount.getAccountNumber());
        Assert.notNull(account, "can't find account with name " + updateAccount.getAccountName());

        repository.save(account);

        log.debug("account {} changes has been saved", updateAccount.getAccountName());
    }

}
