package com.simplesystem.todo;

import com.simplesystem.todo.model.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TodoScheduler {
    private final TodoRepository todoRepository;

    @Scheduled(fixedRateString = "${app.updateTodo.rate}")
    @Transactional
    public void updateTodo() {
        try {
            todoRepository.updateTodo(Status.PAST_DUE, LocalDateTime.now());
        } catch (Exception e) {
            log.error("Scheduled update of Todo entries failed : ", e.getMessage());
        }
    }
}
