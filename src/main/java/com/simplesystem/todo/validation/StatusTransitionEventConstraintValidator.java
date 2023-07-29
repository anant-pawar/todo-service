package com.simplesystem.todo.validation;

import com.simplesystem.todo.model.StatusTransitionEvent;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class StatusTransitionEventConstraintValidator implements ConstraintValidator<DueDateTimeConstraint, StatusTransitionEvent> {
    @Override
    public boolean isValid(final StatusTransitionEvent event, ConstraintValidatorContext constraintValidatorContext) {
        return event != null && !StatusTransitionEvent.TO_PAST_DUE.equals(event);
    }
}
