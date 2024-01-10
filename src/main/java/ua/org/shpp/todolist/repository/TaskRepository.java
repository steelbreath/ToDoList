package ua.org.shpp.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.shpp.todolist.entity.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity,Long> {
}
