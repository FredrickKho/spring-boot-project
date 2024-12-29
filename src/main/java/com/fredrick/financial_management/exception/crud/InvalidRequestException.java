package com.fredrick.financial_management.exception.crud;

public class InvalidRequestException extends RuntimeException{
    private Object errors;
    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRequestException(Throwable cause) {
        super(cause);
    }
    public InvalidRequestException(Object o){
        this.errors = o;
    }
    public Object getErrors() {
        return errors;
    }
}
