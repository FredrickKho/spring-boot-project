package com.fredrick.financial_management.service;

import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    Response<List<Account>> findAll();
    Response<Account> findAccountByUuid(String uuid);
    Response<String> save (String uuid);
    Response<String> deleteById(String uuid);
}
