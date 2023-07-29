package com.simplesystem.todo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class DueDateConstraintValidator implements ConstraintValidator<DueDateTimeConstraint, LocalDateTime> {
    @Override
    public boolean isValid(final LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        return localDateTime != null && localDateTime.isAfter(LocalDateTime.now());
    }
}
