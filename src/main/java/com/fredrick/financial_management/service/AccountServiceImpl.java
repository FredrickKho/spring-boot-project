package com.fredrick.financial_management.service;

import com.fredrick.financial_management.dao.AccountRepository;
import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.exception.auth.AuthEmailFormatNotValid;
import com.fredrick.financial_management.exception.crud.DataNotFoundException;
import com.fredrick.financial_management.request.account.UpdateAccountRequest;
import com.fredrick.financial_management.response.Response;
import com.fredrick.financial_management.validator.AuthValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private AuthValidator authValidator;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AuthValidator authValidator) {
        this.accountRepository = accountRepository;
        this.authValidator = authValidator;
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
        return Response.<Account>builder()
                .data(find.get(0))
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    @Override
    public Response<String> delete(String uuid) {
        List<Account> account = accountRepository.findByUuid(uuid);
        accountRepository.delete(account.get(0));
        return Response.<String>builder()
                .data("")
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    @Override
    public Response<String> save(UpdateAccountRequest request) {
        Account account =
                (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(!request.getName().isEmpty()){
//            account.setName(request.getName());
//        }

        return Response.<String>builder()
                .data("")
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
