package com.fredrick.financial_management.response;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class Response<T> {
    private int code;
    private String status;
    private T data;
    private String errors;
    private long timestamp;

}
