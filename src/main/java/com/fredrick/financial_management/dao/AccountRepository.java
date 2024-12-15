package com.fredrick.financial_management.dao;

import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    Optional<Account> findByEmail(String email);
    List<Account> findByUuid(String uuid);
}
