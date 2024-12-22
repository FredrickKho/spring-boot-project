package com.fredrick.financial_management.exception.auth;

public class TokenExceptionHandler extends RuntimeException{
    public TokenExceptionHandler(String message) {
        super(message);
    }

    public TokenExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenExceptionHandler(Throwable cause) {
        super(cause);
    }
}
