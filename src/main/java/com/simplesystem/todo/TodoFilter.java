package com.simplesystem.todo;

import com.simplesystem.todo.model.Status;
import org.springframework.data.jpa.domain.Specification;

public class TodoFilter {
    public static final String STATUS = "status";

    public static Specification<TodoEntity> byStatus(final Status status) {
        return (todo, criteriaQuery, criteriaBuilder) -> (status == null) ? criteriaBuilder.conjunction() : criteriaBuilder.equal(todo.get(STATUS), status);
    }
}
