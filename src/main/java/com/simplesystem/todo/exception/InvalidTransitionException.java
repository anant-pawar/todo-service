package com.simplesystem.todo.exception;

import com.simplesystem.todo.model.Status;

public class InvalidTransitionException extends RuntimeException {
    public InvalidTransitionException(final String id, final Status from, final Status to) {
        super(String.format("Todo resource %s cannot be transitioned from %s to %s", id, from, to));
    }
}
