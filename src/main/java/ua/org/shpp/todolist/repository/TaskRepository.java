package ua.org.shpp.todolist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.shpp.todolist.entity.TaskEntity;
import ua.org.shpp.todolist.entity.UserEntity;

import java.util.Set;

public interface TaskRepository extends JpaRepository<TaskEntity,Long> {
    Page<TaskEntity> findAllByUserEntity(UserEntity userEntity, Pageable pageable);
}
