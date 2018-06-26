package com.gooooogolf.account.repository;

import com.gooooogolf.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    public Account findByAccountNumber(String accountNumber);
}
