package org.example.service;

import org.example.model.todoModel;
import org.example.model.TodoRequest;
import org.example.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    //MOCK을 사용하는 이유
    //1. 외부 시스템이 의존하지 않고 자체 테스트 가능 (유닛 test은 db, nw 없아도 가능해야 함)
    //2. 실제 DB를 사용하게 되면 민간함 정보. 서비스 중인 데이터가 함부로 변경되어야 함.
    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void add() {
        when (this.todoRepository.save(any(todoModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        TodoRequest expected = new TodoRequest();
        expected.setTitle("Test Title");

        todoModel actual = this.todoService.add(expected);

        assertEquals(expected.getTitle(),actual.getTitle());
    }

    @Test
    void searchById() {
        todoModel entity = new todoModel();
        entity.setId(123L);
        entity.setTitle("TITLE");
        entity.setOrder(0l);
        entity.setCompleted(false);
        Optional<todoModel> optional = Optional.of(entity);

        given(this.todoRepository.findById(anyLong()))
                .willReturn(optional);

        todoModel actual = this.todoService.searchById(123L);

        todoModel expected = optional.get();

        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getTitle(),actual.getTitle());
        assertEquals(expected.getOrder(),actual.getOrder());
        assertEquals(expected.getCompleted(), actual.getCompleted());

    }

    @Test
    public void searchByIdFailed(){
        given(this.todoRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () ->{
            this.todoService.searchById(123L);
        });
    }

}