package com.fredrick.financial_management.controller;

import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.response.Response;
import com.fredrick.financial_management.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/account")
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Response<Account>> findAccountByUuid(@PathVariable String uuid){
        return ResponseEntity.ok(accountService.findAccountByUuid(uuid));
    }

    @GetMapping("")
    public ResponseEntity<Response<List<Account>>> findAccountByUuid(){
        return ResponseEntity.ok(accountService.findAll());
    }

    @PutMapping("")
    public ResponseEntity<Response<Account>> updateAccount(@PathVariable String uuid){
        return null;
    }

    @DeleteMapping("")
    public ResponseEntity<Response<Account>> DeleteAccount(@PathVariable String uuid){
        return null;
    }

}
