package com.simplesystem.todo.model;

import com.simplesystem.todo.validation.StatusTransitionEventConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusTransitionRequest {
    @StatusTransitionEventConstraint(message = "Transition event is required, and can be either of TO_DONE or TO_NOT_DONE")
    private StatusTransitionEvent event;

}
