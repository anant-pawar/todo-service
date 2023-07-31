package com.simplesystem.todo;

import com.simplesystem.todo.model.*;

import java.time.LocalDateTime;

public class TestHelper {
    public static TodoUpdate createTodoUpdate(final String description) {
        final var todoUpdate = new TodoUpdate();
        todoUpdate.setDescription(description);
        return todoUpdate;
    }

    public static StatusTransitionRequest createStatusTransitionRequest(final StatusTransitionEvent statusTransitionEvent) {
        final var statusTransitionRequest = new StatusTransitionRequest();
        statusTransitionRequest.setEvent(statusTransitionEvent);
        return statusTransitionRequest;
    }

    public static TodoCreate createTodoCreate(final String description, final LocalDateTime dueOn) {
        final var todoCreate = new TodoCreate();
        todoCreate.setDescription(description);
        todoCreate.setDueOn(dueOn);
        return todoCreate;
    }

    public static TodoEntity createTodoEntity(final String id, final String description,
                                        final Status status, final LocalDateTime dueOn,
                                        final LocalDateTime doneOn, final LocalDateTime createdAt) {
        final var todoEntity = new TodoEntity();
        todoEntity.setId(id);
        todoEntity.setDescription(description);
        todoEntity.setStatus(status);
        todoEntity.setDueOn(dueOn);
        todoEntity.setDoneOn(doneOn);
        todoEntity.setCreatedAt(createdAt);
        return todoEntity;
    }
}
