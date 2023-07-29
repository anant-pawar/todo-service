package com.simplesystem.todo;

import com.simplesystem.todo.model.Status;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    List<TodoEntity> findAll(Specification<TodoEntity> todo);

    @Modifying
    @Query("update TodoEntity t set t.status = :status where t.dueOn < :dateTime")
    void updateTodo(@Param(value = "status") Status status, @Param(value = "dateTime") LocalDateTime dateTime);

}
