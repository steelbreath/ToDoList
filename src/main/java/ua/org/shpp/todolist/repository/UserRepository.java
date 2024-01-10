package ua.org.shpp.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.org.shpp.todolist.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
}
