package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.todoModel;
import org.example.model.TodoRequest;
import org.example.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

//1	todo 리스트 목록에 아이템을 추가
    public todoModel add(TodoRequest request){
        todoModel todoModel = new todoModel();
        todoModel.setTitle(request.getTitle());
        todoModel.setOrder(request.getOrder());
        todoModel.setCompleted(request.getCompleted());
       // TodoEntity entity = this.todoRepository.save(todoEntity);  save의 return 값은 제너릭
        return this.todoRepository.save(todoModel);
    }

//2	todo  리스트 목록 중 특정 아이템을 조회
    public todoModel searchById (Long id) {
        return this.todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

//3	todo 리스트 전체 목록을 조회
    public List<todoModel> searchAll() {
        return this.todoRepository.findAll();
    }

//4	todo 리스트 목록 중 특정 아이템을 수정
    public todoModel updateById(Long id, TodoRequest request){
        todoModel todoModel = this.searchById(id);
        if (request.getTitle() != null) {
            todoModel.setTitle(request.getTitle());
        }
        if(request.getOrder() != null) {
            todoModel.setOrder(request.getOrder());
        }
        if(request.getCompleted() != null) {
            todoModel.setCompleted(request.getCompleted());
        }
        return this.todoRepository.save(todoModel);
    }

//5	todo 리스트 목록 중 특정 아이템을 삭제
    public void deleteById(Long id){
        this.todoRepository.deleteById(id);
    }

//6	todo 리스트 전체 목록을 삭제
    public void deleteAll(){
        this.todoRepository.deleteAll();
    }

}
