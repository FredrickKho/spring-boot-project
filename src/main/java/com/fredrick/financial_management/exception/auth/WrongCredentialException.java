package com.fredrick.financial_management.exception.auth;

public class WrongCredentialException extends RuntimeException{
    public WrongCredentialException(String message) {
        super(message);
    }

    public WrongCredentialException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongCredentialException(Throwable cause) {
        super(cause);
    }
}
