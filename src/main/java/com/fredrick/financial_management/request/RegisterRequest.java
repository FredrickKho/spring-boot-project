package com.fredrick.financial_management.request;

import com.fredrick.financial_management.enumeration.Country;
import com.fredrick.financial_management.enumeration.Gender;
import com.fredrick.financial_management.validator.EnumValue;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
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
    @EnumValue(enumClass = Gender.class, message = "Invalid gender value")
    private String gender;
    private Date dob;
    private String phonenumber;
    private String lastname;
    private String country;

}
