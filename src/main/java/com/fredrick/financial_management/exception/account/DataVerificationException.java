package com.fredrick.financial_management.exception.account;

public class DataVerificationException extends RuntimeException{
    public Object error;

    public DataVerificationException(Object error) {
        this.error = error;
    }

    public Object getError() {
        return error;
    }

    public DataVerificationException(String message) {
        super(message);
    }

    public DataVerificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataVerificationException(Throwable cause) {
        super(cause);
    }
}
