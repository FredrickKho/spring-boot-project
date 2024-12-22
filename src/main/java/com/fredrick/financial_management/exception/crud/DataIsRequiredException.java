package com.fredrick.financial_management.exception.crud;

import java.util.List;

public class DataIsRequiredException extends RuntimeException{
    private Object errMessage;
    public DataIsRequiredException(Object message) {
        this.errMessage = message;
    }

    public DataIsRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataIsRequiredException(Throwable cause) {
        super(cause);
    }

    public Object getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(Object errMessage) {
        this.errMessage = errMessage;
    }
}
