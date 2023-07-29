package com.simplesystem.todo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DueDateConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DueDateTimeConstraint {

    String message() default "invalid due date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
