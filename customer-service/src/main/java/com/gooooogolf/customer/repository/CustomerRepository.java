package com.gooooogolf.customer.repository;

import com.gooooogolf.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    public Customer findByCitizenId(String citizenId);
}
