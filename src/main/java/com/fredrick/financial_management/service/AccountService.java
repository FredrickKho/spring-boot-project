package com.fredrick.financial_management.service;

import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.request.account.ChangePasswordRequest;
import com.fredrick.financial_management.request.account.UpdateProfileRequest;
import com.fredrick.financial_management.response.Response;

import java.util.List;

public interface AccountService {
    Response<List<Account>> findAll(int page, int size);
    Response<Account> getAccountDetail();
    Response<Account> findAccountByUuid(String uuid);
    Response<String> update(UpdateProfileRequest request);
    Response<String> delete(String uuid);
    Response<String> changePassword(ChangePasswordRequest request);
    Response<String> xChangePassword(String uuid, ChangePasswordRequest request);
    Response<String> xUpdate(String uuid, UpdateProfileRequest request);
    Response<List<String>> getCountry ();
}
