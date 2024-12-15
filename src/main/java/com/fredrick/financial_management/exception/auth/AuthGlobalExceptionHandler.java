package com.fredrick.financial_management.exception.auth;

import com.fredrick.financial_management.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Response<String>> handleException(AuthEmailFormatNotValid exc) {
        Response<String> error = Response.<String>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(null)
                .errors(exc.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Response<String>> handleException(AuthenticationException exc) {
        Response<String> error = Response.<String>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(null)
                .errors(exc.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
