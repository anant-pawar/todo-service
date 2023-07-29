package com.simplesystem.todo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simplesystem.todo.validation.DueDateTimeConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TodoCreate {
    @NotBlank(message = "Description is mandatory, and cannot be blank")
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DueDateTimeConstraint(message = "Due date time is required, and should be after current date time")
    private LocalDateTime dueOn;
}
