package com.fredrick.financial_management.service;

import com.fredrick.financial_management.dao.AccountRepository;
import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.enumeration.AccountRole;
import com.fredrick.financial_management.enumeration.Country;
import com.fredrick.financial_management.enumeration.Gender;
import com.fredrick.financial_management.exception.auth.AuthEmailFormatNotValid;
import com.fredrick.financial_management.exception.crud.DataIsRequiredException;
import com.fredrick.financial_management.exception.crud.DuplicateDataException;
import com.fredrick.financial_management.request.AuthenticationRequest;
import com.fredrick.financial_management.request.RegisterRequest;
import com.fredrick.financial_management.response.AuthenticationResponse;
import com.fredrick.financial_management.response.RegisterResponse;
import com.fredrick.financial_management.response.Response;
import com.fredrick.financial_management.validator.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AuthenticationService {
    @Autowired
    private AccountRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ValidatorUtil authValidator;

    public Response<RegisterResponse> register(@Valid RegisterRequest request) {
        System.out.println("REGISTER REQUEST : "+request);
        if (!authValidator.validateEmail(request.getEmail()))
            throw new AuthEmailFormatNotValid("Email wrong format");
        List<String> errors = new ArrayList<>();
        Gender gender = null;
        Country country = null;
        LocalDate date = null;
        if(request.getFirstname() == null){
            errors.add("First name is empty");
        }
        if(request.getLastname() == null){
            errors.add("Last name is empty");
        }
        if(request.getGender() == null){
            errors.add("Gender is empty");
        } else {
            try {
                gender = Gender.valueOf(request.getGender().toUpperCase());
            }catch (Exception e){
                errors.add("Gender is invalid");
            }
        }
        if(request.getCountry() != null){
            try {
                country = Country.valueOf(request.getCountry().toUpperCase());
            }catch (Exception e){
                errors.add("Country is invalid");
            }
        }
        if(request.getDob() != null){
            if(!ValidatorUtil.isValidLocalDate(request.getDob()))
                errors.add("dob format is invalid (must be YYYY/MM/DD and valid day and month)");
            else
                date = LocalDate.parse(request.getDob());
        }
        if(request.getPhonenumber() != null){
            if(!request.getPhonenumber().matches("[0-9-]+")){
                errors.add("phone number format is invalid");
            }
        }
        if(!errors.isEmpty()){
            throw new DataIsRequiredException(errors);
        }
        var account = Account.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(AccountRole.MEMBER)
                .uuid(UUID.randomUUID().toString())
                .gender(gender)
                .dob(date)
                .phonenumber(request.getPhonenumber())
                .country(country)
                .build();
        System.out.println("ACCOUNT : "+account);
        try {
            repository.save(account);
        } catch (Exception e) {
//            System.out.println(e);
            throw new DuplicateDataException("Credential already exists");
        }
        var jwtToken = jwtService.generateToken(request.getEmail());
        return Response.<RegisterResponse>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .data(new RegisterResponse(jwtToken))
                .build();
    }

    public Response<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        if(request.getEmail() == null){
            if(request.getPassword() == null) {
                throw new DataIsRequiredException("Email and password must be filled");
            }
            throw new DataIsRequiredException("Email must be filled");
        }
        if(request.getPassword() == null){
            throw new DataIsRequiredException("Password must be filled");
        }
        if (!authValidator.validateEmail(request.getEmail()))
            throw new AuthEmailFormatNotValid("Email wrong format");
        var account = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(request.getEmail());
        return Response.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .data(new AuthenticationResponse(jwtToken))
                .build();
    }
}
