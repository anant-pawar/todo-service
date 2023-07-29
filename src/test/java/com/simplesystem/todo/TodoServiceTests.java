package com.simplesystem.todo;

import com.simplesystem.todo.model.Status;
import com.simplesystem.todo.model.TodoCreate;
import com.simplesystem.todo.model.TodoUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTests {
    private TodoRepository repository;
    private TodoMapper mapper;
    private TodoService service;

    @BeforeEach
    public void init() {
        repository = Mockito.mock(TodoRepository.class);
        mapper = Mockito.spy(Mappers.getMapper(TodoMapper.class));
        service = new TodoService(repository, mapper, null);
    }

    @Test
    public void testCreateTodoItem() {
        final var id = UUID.randomUUID().toString();
        final var description = "Buy 1Kg Tomatoes";
        final var dueOn = LocalDateTime.now().plusDays(1);
        final var createdAt = LocalDateTime.now();

        final var todoEntity = createTodoEntity(id, description, Status.NOT_DONE, dueOn, null, createdAt);
        final var todoCreate = createTodoCreate(description, dueOn);

        Mockito.when(mapper.createId()).thenReturn(id);
        Mockito.when(repository.save(ArgumentMatchers.any(TodoEntity.class))).thenReturn(todoEntity);

        final var todo = service.createTodo(todoCreate);

        assertNotNull(todo);
        assertEquals(id, todo.getId());
        assertEquals(description, todo.getDescription());
        assertEquals(Status.NOT_DONE, todo.getStatus());
        assertEquals(dueOn, todo.getDueOn());
        assertEquals(createdAt, todo.getCreatedAt());
    }

    @Test
    public void testUpdateTodoItemDescription() {
        final var id = UUID.randomUUID().toString();
        final var dueOn = LocalDateTime.now().plusDays(1);
        final var createdAt = LocalDateTime.now();

        final var existingTodoEntity = createTodoEntity(id, "Buy 1Kg Tomatoes", Status.DONE, dueOn, null, createdAt);
        final var updatedTodoEntity = createTodoEntity(id, "Buy 2Kg Tomatoes", Status.DONE, dueOn, null, createdAt);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(existingTodoEntity));
        Mockito.when(repository.save(ArgumentMatchers.any(TodoEntity.class))).thenReturn(updatedTodoEntity);

        final var todo = service.updateTodo(id, createTodoUpdate("Buy 2Kg Tomatoes"));

        assertNotNull(todo);
        assertEquals(id, todo.getId());
        assertEquals("Buy 2Kg Tomatoes", todo.getDescription());
        assertEquals(Status.DONE, todo.getStatus());
        assertEquals(dueOn, todo.getDueOn());
        assertEquals(createdAt, todo.getCreatedAt());
    }

    @Test
    @Disabled
    public void testUpdateTodoItemToDone() {
        fail();
    }

    @Test
    @Disabled
    public void testUpdateTodoItemToNotDone() {
        fail();
    }

    @Test
    public void testGetAllNotDoneTodoItems() {
        final var firstTodoEntity = createTodoEntity(UUID.randomUUID().toString(), "Buy 1Kg Tomatoes", Status.NOT_DONE, LocalDateTime.now().plusDays(1), null, LocalDateTime.now());
        final var secondTodoEntity = createTodoEntity(UUID.randomUUID().toString(), "Buy 1Kg Tomatoes", Status.NOT_DONE, LocalDateTime.now().plusDays(2), null, LocalDateTime.now());

        Mockito.when(repository.findAll(ArgumentMatchers.any(Specification.class))).thenReturn(List.of(firstTodoEntity, secondTodoEntity));

        final var todos = service.getTodos(Status.NOT_DONE);

        assertFalse(todos.isEmpty());
    }

    @Test
    public void testGetTodoItem() {
        final var id = UUID.randomUUID().toString();
        final var description = "Buy 1Kg Tomatoes";
        final var dueOn = LocalDateTime.now().plusDays(1);
        final var createdAt = LocalDateTime.now();

        final var todoEntity = createTodoEntity(id, description, Status.DONE, dueOn, null, createdAt);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(todoEntity));

        final var todo = service.getTodoById(id);

        assertNotNull(todo);
        assertEquals(id, todo.getId());
        assertEquals(description, todo.getDescription());
        assertEquals(Status.DONE, todo.getStatus());
        assertEquals(dueOn, todo.getDueOn());
        assertEquals(createdAt, todo.getCreatedAt());
    }

    private TodoUpdate createTodoUpdate(final String description) {
        final var todoUpdate = new TodoUpdate();
        todoUpdate.setDescription(description);
        return todoUpdate;
    }

    private TodoCreate createTodoCreate(final String description, final LocalDateTime dueOn) {
        final var todoCreate = new TodoCreate();
        todoCreate.setDescription(description);
        todoCreate.setDueOn(dueOn);
        return todoCreate;
    }

    private TodoEntity createTodoEntity(final String id, final String description,
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
