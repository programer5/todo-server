package org.example.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.TodoModel;
import org.example.model.TodoRequest;
import org.example.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TodoService todoService;

    private TodoModel expected;

    @BeforeEach
    void setup() {
        expected = new TodoModel();
        expected.setId(123L);
        expected.setTitle("TEST TITLE");
        expected.setOrder(0L);
        expected.setCompleted(false);
    }

    @Test
    void create() throws Exception {
        when(todoService.add(any(TodoRequest.class)))
                .then((i) -> {
                    TodoRequest request = i.getArgument(0, TodoRequest.class);
                    return new TodoModel(expected.getId(), request.getTitle(), expected.getOrder(), expected.getCompleted());
                });

        TodoRequest request = new TodoRequest();
        request.setTitle("ANY TITLE");

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        mvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("ANY TITLE"));
    }

    @Test
    void readOne() {
    }
}