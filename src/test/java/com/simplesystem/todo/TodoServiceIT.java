package com.simplesystem.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TodoServiceIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    public void createTodoTest() {
        final var todoCreate = TestHelper.createTodoCreate("Buy 1Kg Tomatoes", LocalDateTime.now().plusDays(1));

        mockMvc.perform(post("/todos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(todoCreate)))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    public void createTodoTest_InvalidDescription() {
        final var todoCreate = TestHelper.createTodoCreate(Strings.EMPTY, LocalDateTime.now().plusDays(1));

        mockMvc.perform(post("/todos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(todoCreate)))
                .andExpect(status().is4xxClientError());
    }

}
