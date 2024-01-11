package ua.org.shpp.todolist.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ua.org.shpp.todolist.dto.TaskConciseDTO;
import ua.org.shpp.todolist.dto.TaskDTO;
import ua.org.shpp.todolist.dto.UserConciseDTO;
import ua.org.shpp.todolist.dto.UserDTO;
import ua.org.shpp.todolist.entity.TaskEntity;
import ua.org.shpp.todolist.entity.UserEntity;
import ua.org.shpp.todolist.enums.Status;
import ua.org.shpp.todolist.exception.IllegalDataChangeException;
import ua.org.shpp.todolist.exception.TaskNotFoundException;
import ua.org.shpp.todolist.exception.UserNotFoundException;
import ua.org.shpp.todolist.exception.UsernameAlreadyExistException;
import ua.org.shpp.todolist.repository.TaskRepository;
import ua.org.shpp.todolist.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoListService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public ToDoListService(UserRepository userRepository, TaskRepository taskRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<UserDTO> signUp(UserConciseDTO userConciseDTO) {
        Optional<UserEntity> exist = userRepository.findByUsername(userConciseDTO.getUsername());
        if(exist.isPresent()){
            throw new UsernameAlreadyExistException("User with such username has already exist.");
        }
        UserEntity userEntity = modelMapper.map(userConciseDTO, UserEntity.class);
        if (userConciseDTO.getUsername().equals("admin") && userConciseDTO.getPassword().equals("admin")) {
            userEntity.setRoles("ADMIN,USER");
        } else {
            userEntity.setRoles("USER");
        }
        UserDTO userDTO = modelMapper.map(userRepository.save(userEntity), UserDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    public ResponseEntity<UserDTO> getUser(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        return ResponseEntity.ok(modelMapper.map(userEntity, UserDTO.class));
    }

    public void deleteUser(String username) {
        if (userRepository.findByUsername(username).isEmpty()) {
            throw new UserNotFoundException();
        }
        userRepository.deleteUserEntityByUsername(username);
    }

    public ResponseEntity<UserDTO> updateUser(String username, UserConciseDTO userConciseDTO) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        if (!username.equals(userConciseDTO.getUsername())) {
            throw new IllegalDataChangeException("You can't change your username");
        }
        userEntity.setUsername(userConciseDTO.getUsername());
        userEntity.setPassword(userConciseDTO.getPassword());
        UserDTO userDTO = modelMapper.map(userRepository.save(userEntity), UserDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        Page<UserEntity> page = userRepository.findAll(pageable);
        List<UserDTO> users = page.map(userEntity -> modelMapper.map(userEntity, UserDTO.class)).getContent();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public ResponseEntity<TaskDTO> createTask(String username, TaskConciseDTO task) {
        TaskEntity taskEntity = modelMapper.map(task, TaskEntity.class);
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        taskEntity.setCreatedAt(LocalDateTime.now());
        taskEntity.setUserEntity(userEntity);
        TaskDTO taskDTO = modelMapper.map(taskRepository.save(taskEntity), TaskDTO.class);
        return ResponseEntity.ok(taskDTO);
    }

    public ResponseEntity<TaskDTO> getTask(String username, Long id) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
        if(!taskEntity.getUserEntity().equals(userEntity)){
            throw new AccessDeniedException("You can't get another user task!");
        }
        return ResponseEntity.ok(modelMapper.map(taskEntity, TaskDTO.class));
    }

    public void deleteTask(String username, Long id) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        if(!taskEntity.getUserEntity().equals(userEntity)){
            throw new AccessDeniedException("You can't get another user task!");
        }
        taskRepository.deleteById(id);
    }

    public ResponseEntity<TaskDTO> updateTask(String username, Long id, TaskConciseDTO task) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        if(!taskEntity.getUserEntity().equals(userEntity)){
            throw new AccessDeniedException("You can't get another user task!");
        }
        update(taskEntity, task);
        TaskDTO taskDTO = modelMapper.map(taskRepository.save(taskEntity), TaskDTO.class);
        return ResponseEntity.ok(taskDTO);
    }

    private void update(TaskEntity taskEntity, TaskConciseDTO task) {
        Status toUpdate = task.getStatus();
        if(!Status.CONNECTIONS[taskEntity.getStatus().getRowCol()][toUpdate.getRowCol()]){
            throw new IllegalDataChangeException("You can't change task status from "
                    + taskEntity.getStatus() + " to " + toUpdate + "!");
        }
        taskEntity.setStatus(toUpdate);
        taskEntity.setDeadline(task.getDeadline());
        taskEntity.setDescription(task.getDescription());
    }

    public ResponseEntity<List<TaskDTO>> getAllTasks(String username, Pageable pageable) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        Page<TaskEntity> page = taskRepository.findAllByUserEntity(userEntity, pageable);
        List<TaskDTO> tasks = page.map(taskEntity -> modelMapper.map(taskEntity, TaskDTO.class)).getContent();
        if (tasks.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

}
