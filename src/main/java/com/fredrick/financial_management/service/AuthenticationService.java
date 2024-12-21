package com.fredrick.financial_management.service;

import com.fredrick.financial_management.dao.AccountRepository;
import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.enumeration.AccountRole;
import com.fredrick.financial_management.exception.auth.AuthEmailFormatNotValid;
import com.fredrick.financial_management.exception.auth.WrongCredentialException;
import com.fredrick.financial_management.exception.crud.DataIsRequiredException;
import com.fredrick.financial_management.exception.crud.DuplicateDataException;
import com.fredrick.financial_management.request.AuthenticationRequest;
import com.fredrick.financial_management.request.RegisterRequest;
import com.fredrick.financial_management.response.AuthenticationResponse;
import com.fredrick.financial_management.response.RegisterResponse;
import com.fredrick.financial_management.response.Response;
import com.fredrick.financial_management.validator.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private AuthValidator authValidator;

    public Response<RegisterResponse> register(RegisterRequest request) {
        System.out.println(request);
        if (!authValidator.validateEmail(request.getEmail()))
            throw new AuthEmailFormatNotValid("Email wrong format");
        var account = Account.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(AccountRole.MEMBER)
                .uuid(UUID.randomUUID().toString())
                .build();
        try {
            repository.save(account);
        } catch (Exception e) {
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
