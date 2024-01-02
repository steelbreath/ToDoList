package ua.org.shpp.todolist.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import ua.org.shpp.todolist.Status;
import ua.org.shpp.todolist.dto.TaskConciseDTO;
import ua.org.shpp.todolist.repository.TaskRepository;

import java.time.LocalDateTime;

class TaskServiceTest {

    private TaskService taskService;
    private TaskRepository taskRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp(){
        taskRepository = Mockito.mock(TaskRepository.class);
        modelMapper = new ModelMapper();
        taskService = new TaskService(taskRepository,modelMapper);
    }

    @Test
    void createTaskTest() {
        TaskConciseDTO task = new TaskConciseDTO(Status.PLANNED,"some",
                LocalDateTime.of(2024,2,12,10,10,10));
        taskService.createTask(task);
    }
}