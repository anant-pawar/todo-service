package com.simplesystem.todo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoUpdate {
    @NotBlank(message = "Description is mandatory, and cannot be blank")
    private String description;

}
