package com.project.SpringSecurity.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ListContainsValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RolesMustInclude {
    String message() default "List must include the specified elements";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] elements();
}
