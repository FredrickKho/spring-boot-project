package com.fredrick.financial_management.controller;

import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.request.account.ChangePasswordRequest;
import com.fredrick.financial_management.request.account.UpdateProfileRequest;
import com.fredrick.financial_management.response.Response;
import com.fredrick.financial_management.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/account")
@Slf4j
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping(path = "/getAccountDetails")
    public ResponseEntity<Response<Account>> getAccountDetail(){
        log.info("Hitting GET /api/account/getAccountDetails");
        return ResponseEntity.ok(accountService.getAccountDetail());
    }
    @PatchMapping(path = "/update")
    public ResponseEntity<Response<String>> updateAccount(@RequestBody UpdateProfileRequest request){
        log.info("Hitting PATCH /api/account/update");
        return ResponseEntity.ok(accountService.update(request));
    }

    @PatchMapping(path = "/changePassword")
    public ResponseEntity<Response<String>> changePassword(ChangePasswordRequest request){
        log.info("Hitting PATCH /api/account/changePassword");
        return ResponseEntity.ok(accountService.changePassword(request));
    }

}
