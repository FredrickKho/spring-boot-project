package com.fredrick.financial_management.service;

import com.fredrick.financial_management.dao.AccountRepository;
import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.enumeration.Country;
import com.fredrick.financial_management.enumeration.Gender;
import com.fredrick.financial_management.exception.auth.AuthEmailFormatNotValid;
import com.fredrick.financial_management.exception.crud.InvalidRequestException;
import com.fredrick.financial_management.request.account.ChangePasswordRequest;
import com.fredrick.financial_management.request.account.UpdateProfileRequest;
import com.fredrick.financial_management.response.Pagination;
import com.fredrick.financial_management.response.Response;
import com.fredrick.financial_management.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private ValidatorUtil authValidator;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, ValidatorUtil authValidator) {
        this.accountRepository = accountRepository;
        this.authValidator = authValidator;

    }

    @Override
    public Response<List<Account>> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Account> acc = accountRepository.findAll(pageable);
        Pagination pagination = Pagination.builder()
                .page(page)
                .size(size)
                .totalPage(acc.getTotalPages())
                .totalSize((int) acc.getTotalElements())
                .build();
        Response<List<Account>> response = Response.<List<Account>>builder()
                .data(acc.getContent())
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .pagination(pagination)
                .build();
        return response;
    }

    @Override
    public Response<Account> getAccountDetail() {
        Account account =
                (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.<Account>builder()
                .data(account)
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    @Override
    public Response<Account> findAccountByUuid(String uuid) {
        Account find;
        try {
            find = accountRepository.findByUuid(uuid).get(0);
        }catch (Exception e){
            find = null;
        }
        return Response.<Account>builder()
                .data(find)
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
    public Response<String> changePassword(ChangePasswordRequest request) {
        Account account =
                (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (encoder.matches(request.getOldPassword(), account.getPassword())) {
            account.setPassword(encoder.encode(request.getNewPassword()));
            accountRepository.save(account);
        } else {
            throw new AuthEmailFormatNotValid("The current password is wrong");
        }
        return Response.<String>builder()
                .data("")
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    @Override
    public Response<String> xChangePassword(String uuid, ChangePasswordRequest request) {
        List<String> error = new ArrayList<>();
        if (request.getOldPassword() == null) {
            error.add("Old Password must be filled");
        } else if (request.getOldPassword().length() < 6) {
            error.add("Old Password length must be more than 6 characters");
        }
        if (request.getNewPassword() == null) {
            error.add("New Password must be filled");
        } else if (request.getNewPassword().length() < 7) {
            error.add("New Password length must be more than 6 characters");
        }
        if(!error.isEmpty()){
            throw new InvalidRequestException(error);
        }
        Account account = accountRepository.findByUuid(uuid).get(0);
        if (encoder.matches(request.getOldPassword(), account.getPassword())) {
            account.setPassword(encoder.encode(request.getNewPassword()));
            accountRepository.save(account);
        } else {
            throw new InvalidRequestException("The old password is wrong");
        }

        return Response.<String>builder()
                .data("")
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    @Override
    public Response<String> update(UpdateProfileRequest request) {
        Account account =
                (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> errors = new ArrayList<>();
        if (request.getFirstname() != null) {
            account.setFirstname(request.getFirstname());
        }
        if (request.getLastname() != null) {
            account.setLastname(request.getLastname());
        }
        if (request.getDob() != null) {
            if (ValidatorUtil.isValidLocalDate(request.getDob()))
                account.setDob(LocalDate.parse(request.getDob()));
            else
                errors.add("dob format is invalid (must be YYYY/MM/DD and valid day and month)");
        }
        if (request.getPhonenumber() != null) {
            if (request.getPhonenumber().matches("[0-9-]+")) {
                account.setFirstname(request.getFirstname());
            } else {
                errors.add("phone number format is invalid");
            }
        }
        if (request.getGender() != null) {
            try {
                Gender gender = Gender.valueOf(request.getGender().toUpperCase());
                account.setGender(gender);
            } catch (Exception e) {
                errors.add("Gender is invalid");
            }
        }
        if (request.getCountry() != null) {
            try {
                Country country = Country.valueOf(request.getCountry().toUpperCase());
                account.setCountry(country);
            } catch (Exception e) {
                errors.add("Country is invalid");
            }
        }
        if (!errors.isEmpty()) {
            throw new InvalidRequestException(errors);
        }
        accountRepository.save(account);
        return Response.<String>builder()
                .data("")
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public Response<String> xUpdate(String uuid, UpdateProfileRequest request) {
        Account account = accountRepository.findByUuid(uuid).get(0);
        List<String> errors = new ArrayList<>();
        if (request.getFirstname() != null) {
            account.setFirstname(request.getFirstname());
        }
        if (request.getLastname() != null) {
            account.setLastname(request.getLastname());
        }
        if (request.getDob() != null) {
            if (ValidatorUtil.isValidLocalDate(request.getDob()))
                account.setDob(LocalDate.parse(request.getDob()));
            else
                errors.add("dob format is invalid (must be YYYY/MM/DD and valid day and month)");
        }
        if (request.getPhonenumber() != null) {
            if (request.getPhonenumber().matches("[0-9-]+")) {
                account.setFirstname(request.getFirstname());
            } else {
                errors.add("phone number format is invalid");
            }
        }
        if (request.getGender() != null) {
            try {
                Gender gender = Gender.valueOf(request.getGender().toUpperCase());
                account.setGender(gender);
            } catch (Exception e) {
                errors.add("Gender is invalid");
            }
        }
        if (request.getCountry() != null) {
            try {
                String enumString = request.getCountry().replaceAll(" ", "_").toUpperCase();
                Country country = Country.valueOf(enumString);
                account.setCountry(country);
            } catch (Exception e) {
                errors.add("Country is invalid");
            }
        }
        if (!errors.isEmpty()) {
            throw new InvalidRequestException(errors);
        }
        accountRepository.save(account);
        return Response.<String>builder()
                .data("")
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
