package com.fredrick.financial_management.request.account;

import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Data
public class UpdateProfileRequest {
    @Size(max = 50)
    private String firstname;
    private String gender;
    private String dob;
    private String phonenumber;
    private String lastname;
    private String country;
}
