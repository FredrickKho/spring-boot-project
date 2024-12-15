package com.fredrick.financial_management.service;

import com.fredrick.financial_management.dao.AccountRepository;
import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.exception.crud.DataNotFoundException;
import com.fredrick.financial_management.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Response<List<Account>> findAll() {
        Response<List<Account>> acc = Response.<List<Account>>builder()
                .data(accountRepository.findAll())
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
        return acc;
    }

    @Override
    public Response<Account> findAccountByUuid(String uuid) {
        List<Account> find = accountRepository.findByUuid(uuid);
        Account account = null;
        if (!find.isEmpty()){
            throw new DataNotFoundException("Account not found");
        }
        Response<Account> acc = Response.<Account>builder()
                .data(account)
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
        return acc;
    }

    @Override
    public Response<String> save(String uuid) {
        List<Account> find = accountRepository.findByUuid(uuid);
        Account account = null;
        if (!find.isEmpty()){
            account = find.get(0);
        }
        return null;
    }

    @Override
    public Response<String> deleteById(String uuid) {
        return null;
    }


}
