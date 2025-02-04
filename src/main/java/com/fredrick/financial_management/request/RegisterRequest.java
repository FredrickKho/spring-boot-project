package com.fredrick.financial_management.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Size(max = 50)
    private String firstname;
    private String email;
    private String password;
    private String gender;
    private String dob;
    private String phonenumber;
    private String lastname;
    private String country;
}
