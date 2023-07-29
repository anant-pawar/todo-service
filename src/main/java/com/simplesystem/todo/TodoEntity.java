package com.simplesystem.todo;

import com.simplesystem.todo.model.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "todo")
@Getter
@Setter
public class TodoEntity {
    @Id
    private String id;

    private String description;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Column(name = "due_on")
    private LocalDateTime dueOn;

    @Column(name = "done_on")
    private LocalDateTime doneOn;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
