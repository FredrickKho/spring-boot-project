package com.fredrick.financial_management.request.account;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdateAccountRequest {
    @Size(max = 100)
    private String name;
    private String email;

}
