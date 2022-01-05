package org.example.repository;

import org.example.model.todoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//TodoEntity 만 DB에 저장하면 됨
//TodoEntity를 위한 Repo(Jpa 상속)
//메소드가 없지만 사용 가능

@Repository
public interface TodoRepository extends JpaRepository<todoModel, Long> {
}
