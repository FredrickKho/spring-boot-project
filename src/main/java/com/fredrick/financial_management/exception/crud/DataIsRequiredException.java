package com.fredrick.financial_management.exception.crud;

import java.util.List;

public class DataIsRequiredException extends RuntimeException{
    public DataIsRequiredException(String message) {
        super(message);
    }

    public DataIsRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataIsRequiredException(Throwable cause) {
        super(cause);
    }
}
