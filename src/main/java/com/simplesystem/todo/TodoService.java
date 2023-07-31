package com.simplesystem.todo;

import com.simplesystem.todo.exception.TodoNotFoundException;
import com.simplesystem.todo.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.simplesystem.todo.TodoFilter.byStatus;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository repository;

    private final TodoMapper mapper;

    private final StateMachineFactory<Status, StatusTransitionEvent> stateMachineFactory;

    public Todo createTodo(final TodoCreate todoCreate) {
        final var todoEntity = mapper.createTodoEntity(todoCreate);
        final var persistedTodoEntity = repository.save(todoEntity);
        return mapper.createTodo(persistedTodoEntity);
    }

    public Todo updateTodo(final String id, final TodoUpdate todoUpdate) {
        final var existingTodoEntity = repository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        final var todoEntity = mapper.createTodoEntity(existingTodoEntity, todoUpdate);
        final var updatedTodoEntity = repository.save(todoEntity);
        return mapper.createTodo(updatedTodoEntity);
    }

    public Status transitionTodo(final String id, final StatusTransitionRequest statusTransitionRequest) {
        final var existingTodoEntity = repository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        final var stateMachine = buildStateMachine(existingTodoEntity);
        final var messageBuilder = (Status.NOT_DONE.equals(existingTodoEntity.getStatus()) && LocalDateTime.now().isAfter(existingTodoEntity.getDueOn()))
                ? MessageBuilder.withPayload(StatusTransitionEvent.TO_PAST_DUE) : MessageBuilder.withPayload(statusTransitionRequest.getEvent());
        stateMachine.sendEvent(
                messageBuilder.setHeader("TODO", existingTodoEntity).build());
        return stateMachine.getState().getId();
    }

    public Todo getTodoById(final String id) {
        final var todoEntity = repository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        return mapper.createTodo(todoEntity);
    }

    public List<Todo> getTodos(final Status status) {
        return repository.findAll(byStatus(status))
                .stream()
                .map(mapper::createTodo)
                .collect(Collectors.toList());
    }

    private StateMachine<Status, StatusTransitionEvent> buildStateMachine(final TodoEntity todoEntity) {
        final var stateMachine = this.stateMachineFactory.getStateMachine(todoEntity.getId());
        stateMachine.stop();
        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.resetStateMachine(new DefaultStateMachineContext<>(todoEntity.getStatus(), null, null, null));
                });
        stateMachine.start();
        return stateMachine;
    }
}
