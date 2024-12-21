package com.fredrick.financial_management.service;

import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.request.account.UpdateAccountRequest;
import com.fredrick.financial_management.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    Response<List<Account>> findAll();
    Response<Account> findAccountByUuid(String uuid);
    Response<String> save(UpdateAccountRequest request);
    Response<String> delete(String uuid);
}
