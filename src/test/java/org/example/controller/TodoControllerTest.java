package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.todoModel;
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

//controller test는 api를 띄운다음에 진행 그래서 서비스보다는 좀 더 오래걸림
@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TodoService todoService;

    private todoModel expected;

    @BeforeEach
    void setup(){
        this.expected = new todoModel();
        this.expected.setId(123L);
        this.expected.setTitle("TEST TITLE");
        this.expected.setOrder(0l);
        this.expected.setCompleted(false);
    }

    @Test
    void create() throws Exception {
        //create는 서비스에서 todo ruquest를 받으면 받은 req를 기반으로 새로운 entity 생성
        when(this.todoService.add(any(TodoRequest.class)))
                .then((i) -> {
                    TodoRequest request = i.getArgument(0, TodoRequest.class);
                    return new todoModel(this.expected.getId(), request.getTitle(),
                                          this.expected.getOrder(),this.expected.getCompleted());
                });

        TodoRequest request = new TodoRequest();
        request.setTitle("ANY TITLE");

        //request를 body에 넣어야 함. object를 넣어줄스는 없어서 mapper 사용
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request); //request가 string 형태로

        this.mvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                         .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("ANY TITLE"));
    }

    @Test
    void readOne() {
    }
}