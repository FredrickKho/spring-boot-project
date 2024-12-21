package com.fredrick.financial_management.exception.auth;

public class InvalidTokenHandler extends RuntimeException{
    public InvalidTokenHandler(String message) {
        super(message);
    }

    public InvalidTokenHandler(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTokenHandler(Throwable cause) {
        super(cause);
    }
}
