package ua.org.shpp.todolist;

import ua.org.shpp.todolist.dto.TaskConciseDTO;
import ua.org.shpp.todolist.dto.TaskDTO;
import ua.org.shpp.todolist.dto.UserConciseDTO;
import ua.org.shpp.todolist.dto.UserDTO;
import ua.org.shpp.todolist.entity.TaskEntity;
import ua.org.shpp.todolist.entity.UserEntity;
import ua.org.shpp.todolist.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public class ClassesForTest {
    public static final UserEntity USER_ENTITY =
            new UserEntity("bob","bob","USER", List.of());
    public static final UserEntity ADMIN_ENTITY =
            new UserEntity("admin","admin","ADMIN,USER", List.of());
    public static final UserConciseDTO USER_CONCISE_DTO =
            new UserConciseDTO("bob","bob");
    public static final UserDTO USER_DTO =
            new UserDTO("bob","USER");
    public static final UserDTO ADMIN_DTO =
            new UserDTO("bob","ADMIN,USER'");
    public static TaskEntity TASK_ENTITY =
            new TaskEntity(1L, Status.PLANNED, "some task",
                    LocalDateTime.now(),LocalDateTime.now(),USER_ENTITY);
    public static TaskConciseDTO TASK_CONCISE_DTO =
            new TaskConciseDTO(Status.PLANNED, "some task", LocalDateTime.now());
    public static final TaskDTO TASK_DTO =
            new TaskDTO(1L, Status.PLANNED, "some task", LocalDateTime.now());

}
