package org.example.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponse {
    private Long id;
    private String title;
    private Long order;
    private Boolean completed;
    private String url;

    //TodoEntity를 parameter로 받는 생성자 추가
    public TodoResponse(todoModel todoModel) {
        this.id = todoModel.getId();
        this.title = todoModel.getTitle();
        this.order = todoModel.getOrder();
        this.completed = todoModel.getCompleted();

        this.url = "http://localhost:8080/" + this.id;
        //이렇게 하드코딩이 좋은건 아님*baseurl 변경될때마다 수정배포가 필요햠. 따라서 보통 config가 property로 뺴줌
    }

}
