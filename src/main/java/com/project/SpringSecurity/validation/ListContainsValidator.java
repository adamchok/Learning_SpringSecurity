package com.project.SpringSecurity.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;

public class ListContainsValidator implements ConstraintValidator<RolesMustInclude, List<String>> {
    private List<String> requiredElements;

    @Override
    public void initialize(RolesMustInclude constraintAnnotation) {
        this.requiredElements = List.of(constraintAnnotation.elements());
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        boolean containsOnlyRequiredElements = new HashSet<>(requiredElements).containsAll(value);
        boolean containsAtLeastOneRequiredElement = value.stream().anyMatch(requiredElements::contains);

        if (!containsOnlyRequiredElements) {
            context.buildConstraintViolationWithTemplate("The list contains elements not allowed")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }

        if (!containsAtLeastOneRequiredElement) {
            context.buildConstraintViolationWithTemplate("The list does not contain any of the required elements")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }

        return true;
    }
}
