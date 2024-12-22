package com.fredrick.financial_management.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValue,String>{
    private Class<? extends Enum<?>> enumClass;
    @Override
    public void initialize(EnumValue annotation) {
        enumClass = annotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Assuming null is valid. You may also use @NotNull to enforce a check for null.
        }

        try {
            // Use enumClass to validate the value (case-insensitive)
            Enum<?>[] enumConstants = enumClass.getEnumConstants();
            for (Enum<?> constant : enumConstants) {
                if (constant.name().equalsIgnoreCase(value)) {
                    return true; // Found a match (case-insensitive)
                }
            }
            return false; // not found
        } catch (IllegalArgumentException e) {
            return false; // Invalid enum class or invalid value
        }
    }
}
