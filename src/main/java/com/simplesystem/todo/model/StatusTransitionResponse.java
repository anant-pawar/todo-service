package com.simplesystem.todo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StatusTransitionResponse {
    private final Status status;
}
