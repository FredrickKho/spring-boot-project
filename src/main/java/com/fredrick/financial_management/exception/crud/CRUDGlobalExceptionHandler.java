package com.fredrick.financial_management.exception.crud;

import com.fredrick.financial_management.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CRUDGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Response<String>> handleException(DuplicateDataException exc) {
        Response<String> error = Response.<String>builder()
                .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .status(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                .data(null)
                .errors(exc.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<Response<Object>> handleException(DataIsRequiredException exc) {
        System.out.println("DataIsRequiredException Called :"+exc.getErrMessage());
        Response<Object> error = Response.<Object>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errors(exc.getErrMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Response<String>> handleException(DataNotFoundException exc) {
        System.out.println("Caught DataNotFoundException exception: ");
        Response<String> error = Response.<String>builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.getReasonPhrase())
                .data(null)
                .errors(exc.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<Response<Object>> handleException(InvalidRequestException exc) {
        System.out.println("Caught InvalidRequestException exception: ");
        Response<Object> error = Response.<Object>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errors(exc.getErrors() == null ? exc.getMessage() : exc.getErrors())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Response<Object>> handleException(MissingServletRequestParameterException exc) {
        System.out.println("Caught MissingServletRequestParameterException exception: ");
        Response<Object> error = Response.<Object>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errors(exc.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<Response<Object>> handleException(HttpMessageNotReadableException exc) {
        System.out.println("Caught HttpMessageNotReadableException exception: ");
        Response<Object> error = Response.<Object>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errors(exc.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
