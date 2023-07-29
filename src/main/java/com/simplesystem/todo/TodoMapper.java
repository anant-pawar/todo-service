package com.simplesystem.todo;

import com.simplesystem.todo.model.Status;
import com.simplesystem.todo.model.Todo;
import com.simplesystem.todo.model.TodoCreate;
import com.simplesystem.todo.model.TodoUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    default Status createStatus() {
        return Status.NOT_DONE;
    }

    default LocalDateTime createCreatedAt() {
        return LocalDateTime.now();
    }

    default String createId() {
        return UUID.randomUUID().toString();
    }

    @Mapping(target = "status", expression = "java(createStatus())")
    @Mapping(target = "createdAt", expression = "java(createCreatedAt())")
    @Mapping(target = "id", expression = "java(createId())")
    @Mapping(target = "doneOn", expression = "java(null)")
    TodoEntity createTodoEntity(TodoCreate todoCreate);

    @Mapping(source = "todoUpdate.description", target = "description")
    TodoEntity createTodoEntity(TodoEntity todoEntity, TodoUpdate todoUpdate);

    Todo createTodo(TodoEntity entity);
}
