package com.fredrick.financial_management.controller;

import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.request.account.UpdateAccountRequest;
import com.fredrick.financial_management.response.Response;
import com.fredrick.financial_management.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/account")
@Slf4j
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

    @GetMapping("/all")
    public ResponseEntity<Response<List<Account>>> findAll(){
        return ResponseEntity.ok(accountService.findAll());
    }

    @DeleteMapping("/{uuid}/delete")
    public ResponseEntity<Response<String>> DeleteAccount(@PathVariable String uuid){
        return ResponseEntity.ok(accountService.delete(uuid));
    }

    @PatchMapping(path = "/update")
    public ResponseEntity<Response<String>> updateAccount(@RequestBody UpdateAccountRequest request){
        log.info("Hitting PATCH /api/account/update");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(((Account)authentication.getPrincipal()).get);
        return ResponseEntity.ok(accountService.save(request));
    }
}
