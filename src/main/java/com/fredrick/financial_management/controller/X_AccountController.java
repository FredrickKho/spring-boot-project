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

import java.util.List;

@RestController
@RequestMapping("/api/x-account/")
@Slf4j
public class X_AccountController {
    private AccountService accountService;

    @Autowired
    public X_AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping("/{uuid}")
    public ResponseEntity<Response<Account>> findAccountByUuid(@PathVariable String uuid){
        log.info("Hitting GET /api/x-account/{uuid}");
        return ResponseEntity.ok(accountService.findAccountByUuid(uuid));
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<Account>>> findAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        log.info("Hitting GET /api/x-account/all");
        return ResponseEntity.ok(accountService.findAll(page, size));
    }

    @DeleteMapping("/{uuid}/delete")
    public ResponseEntity<Response<String>> DeleteAccount(@PathVariable String uuid){
        log.info("Hitting DELETE /api/x-account/delete");
        return ResponseEntity.ok(accountService.delete(uuid));
    }
    @PatchMapping(path = "/{uuid}/update")
    public ResponseEntity<Response<String>> updateAccount(@PathVariable String uuid, @RequestBody UpdateProfileRequest request){
        log.info("Hitting PATCH /api/x-account/update");
        //        System.out.println(((Account)authentication.getPrincipal()).get);
        return ResponseEntity.ok(accountService.xUpdate(uuid, request));
    }

    @PatchMapping(path = "/{uuid}/changePassword")
    public ResponseEntity<Response<String>> changePassword(@PathVariable String uuid,@RequestBody ChangePasswordRequest request){
        log.info("Hitting PATCH /api/x-account/uuid/changePassword");
        return ResponseEntity.ok(accountService.xChangePassword(uuid,request));
    }
}
