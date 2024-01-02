package ua.org.shpp.todolist.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.org.shpp.todolist.Status;
import ua.org.shpp.todolist.dto.TaskConciseDTO;
import ua.org.shpp.todolist.dto.TaskDTO;
import ua.org.shpp.todolist.entity.TaskEntity;
import ua.org.shpp.todolist.exception.IllegalStatusChangeException;
import ua.org.shpp.todolist.exception.TaskNotFoundException;
import ua.org.shpp.todolist.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<TaskDTO> createTask(TaskConciseDTO task) {
        TaskEntity taskEntity = modelMapper.map(task, TaskEntity.class);
        taskEntity.setCreatedAt(LocalDateTime.now());
        TaskDTO taskDTO = modelMapper.map(taskRepository.save(taskEntity), TaskDTO.class);
        return ResponseEntity.ok(taskDTO);
    }

    public ResponseEntity<TaskDTO> getTask(Long id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
        return ResponseEntity.ok(modelMapper.map(taskEntity, TaskDTO.class));
    }

    public void deleteTask(Long id) {
        if (taskRepository.findById(id).isEmpty()) {
            throw new TaskNotFoundException();
        }
        taskRepository.deleteById(id);
    }

    public ResponseEntity<TaskDTO> updateTask(Long id, TaskConciseDTO task) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
        update(taskEntity, task);
        TaskDTO taskDTO = modelMapper.map(taskRepository.save(taskEntity), TaskDTO.class);
        return ResponseEntity.ok(taskDTO);
    }

    private void update(TaskEntity taskEntity, TaskConciseDTO task) {
        Status toUpdate = task.getStatus();
        if(!Status.CONNECTIONS[taskEntity.getStatus().getRowCol()][toUpdate.getRowCol()]){
            throw new IllegalStatusChangeException("You can't change task status from "
                    + taskEntity.getStatus() + " to " + toUpdate + "!");
        }
        taskEntity.setStatus(toUpdate);
        taskEntity.setDeadline(task.getDeadline());
        taskEntity.setDescription(task.getDescription());
    }

    public ResponseEntity<List<TaskDTO>> getAllTask(Pageable pageable) {
        Page<TaskEntity> page = taskRepository.findAll(pageable);
        List<TaskDTO> tasks = page.map(taskEntity -> modelMapper.map(taskEntity, TaskDTO.class)).getContent();
        if (tasks.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

}
