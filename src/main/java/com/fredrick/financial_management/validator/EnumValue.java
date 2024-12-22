package com.fredrick.financial_management.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumValue {
    // Default error message
    String message() default "No custom message for EnumValidator";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // The enum class that is validated
    Class<? extends Enum<?>> enumClass();
}
