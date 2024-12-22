package com.fredrick.financial_management.controller;


import com.fredrick.financial_management.request.AuthenticationRequest;
import com.fredrick.financial_management.request.RegisterRequest;
import com.fredrick.financial_management.response.AuthenticationResponse;
import com.fredrick.financial_management.response.RegisterResponse;
import com.fredrick.financial_management.response.Response;
import com.fredrick.financial_management.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<Response<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<Response<AuthenticationResponse>> auth(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

}
