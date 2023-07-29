package com.simplesystem.todo;

import com.simplesystem.todo.model.Status;
import com.simplesystem.todo.model.StatusTransitionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.time.LocalDateTime;
import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class TodoStateMachineConfig extends StateMachineConfigurerAdapter<Status, StatusTransitionEvent> {
    final TodoRepository todoRepository;

    @Override
    public void configure(final StateMachineStateConfigurer<Status, StatusTransitionEvent> states) throws Exception {
        states
                .withStates()
                .initial(Status.NOT_DONE)
                .states(EnumSet.allOf(Status.class));
    }

    @Override
    public void configure(final StateMachineTransitionConfigurer<Status, StatusTransitionEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(Status.NOT_DONE).target(Status.DONE).event(StatusTransitionEvent.TO_DONE)
                .action(context -> updateTodo(context, LocalDateTime.now()))
                .and()
                .withExternal()
                .source(Status.DONE).target(Status.NOT_DONE).event(StatusTransitionEvent.TO_NOT_DONE)
                .action(context -> updateTodo(context, null))
                .and()
                .withExternal()
                .source(Status.NOT_DONE).target(Status.PAST_DUE).event(StatusTransitionEvent.TO_PAST_DUE);
    }

    private void updateTodo(final StateContext<Status, StatusTransitionEvent> context, final LocalDateTime doneOn) {
        final var todoEntity = (TodoEntity) context.getMessageHeader("TODO");
        todoEntity.setStatus(context.getTarget().getId());
        todoEntity.setDoneOn(doneOn);
        todoRepository.save(todoEntity);
    }
}
