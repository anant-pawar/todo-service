package com.simplesystem.todo.exception;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(final String id) {
        super(String.format("Todo resource %s not found", id));
    }
}
