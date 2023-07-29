package com.simplesystem.todo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StatusTransitionEventConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StatusTransitionEventConstraint {

    String message() default "invalid status transition";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
