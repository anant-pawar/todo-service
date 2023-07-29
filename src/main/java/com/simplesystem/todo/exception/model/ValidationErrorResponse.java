package com.simplesystem.todo.exception.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorResponse {
    private final List<Violation> violations = new ArrayList<>();

    @RequiredArgsConstructor
    @Getter
    public static class Violation {
        private final String fieldName;

        private final String message;
    }
}
