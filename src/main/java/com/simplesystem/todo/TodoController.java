package com.simplesystem.todo;

import com.simplesystem.todo.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService service;

    @PostMapping
    public ResponseEntity<Void> createTodo(@Valid @RequestBody TodoCreate todoCreate) {
        final var todo = service.createTodo(todoCreate);
        final var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(todo.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{id}")
    public void updateTodo(@PathVariable("id") String id, @Valid @RequestBody TodoUpdate todoUpdate) {
        service.updateTodo(id, todoUpdate);
    }

    @PutMapping("/{id}/status/transition")
    public StatusTransitionResponse transitionTodo(@PathVariable("id") String id, @RequestBody StatusTransitionRequest transition) {
        return new StatusTransitionResponse(
                service.transitionTodo(id, transition));
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable("id") String id) {
        return service.getTodoById(id);
    }

    @GetMapping
    public List<Todo> getTodos(@RequestParam(name = "status", required = false) Status status) {
        return service.getTodos(status);
    }

}
