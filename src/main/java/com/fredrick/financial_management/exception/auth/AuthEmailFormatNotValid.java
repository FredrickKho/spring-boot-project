package com.fredrick.financial_management.exception.auth;

public class AuthEmailFormatNotValid extends RuntimeException{
    public AuthEmailFormatNotValid(String message) {
        super(message);
    }

    public AuthEmailFormatNotValid(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthEmailFormatNotValid(Throwable cause) {
        super(cause);
    }
}
