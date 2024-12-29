package com.fredrick.financial_management.exception.account;

import com.fredrick.financial_management.exception.crud.InvalidRequestException;
import com.fredrick.financial_management.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalAccountExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Response<Object>> handleException(DataVerificationException exc) {
        System.out.println("Caught DataVerificationException exception: ");
        Response<Object> error = Response.<Object>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errors(exc.getError())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
