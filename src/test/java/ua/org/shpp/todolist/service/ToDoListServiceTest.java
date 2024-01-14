package ua.org.shpp.todolist.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import ua.org.shpp.todolist.dto.TaskConciseDTO;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static ua.org.shpp.todolist.ClassesForTest.*;

@ExtendWith(MockitoExtension.class)
class ToDoListServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private ToDoListService toDoListService;

    @BeforeEach
    void setUp() {
        toDoListService = new ToDoListService(userRepository, taskRepository, modelMapper);
    }

    @Test
    void signUpTest() {
        Mockito.when(userRepository.findByUsername("bob"))
                .thenReturn(Optional.of(USER_ENTITY))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.empty());
        Mockito.when(modelMapper.map(any(), any()))
                .thenReturn(USER_ENTITY)
                .thenReturn(USER_DTO)
                .thenReturn(ADMIN_ENTITY)
                .thenReturn(ADMIN_DTO);
        Assertions.assertThrows(UsernameAlreadyExistException.class,
                () -> toDoListService.signUp(USER_CONCISE_DTO));
        UserDTO userDTO = toDoListService.signUp(USER_CONCISE_DTO).getBody();
        Assertions.assertEquals(userDTO, USER_DTO);
        Assertions.assertEquals(userDTO.getRoles(), USER_DTO.getRoles());
        userDTO = toDoListService.signUp(USER_CONCISE_DTO).getBody();
        Assertions.assertEquals(userDTO, ADMIN_DTO);
        Assertions.assertEquals(userDTO.getRoles(), ADMIN_DTO.getRoles());
    }

    @Test
    void getUserTest() {
        Mockito.when(userRepository.findByUsername("bob"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(USER_ENTITY));
        Mockito.when(modelMapper.map(any(), any()))
                .thenReturn(USER_DTO);
        Assertions.assertThrows(UserNotFoundException.class,
                () -> toDoListService.getUser("bob"));
        Assertions.assertEquals(toDoListService.getUser("bob").getBody().getUsername(), "bob");
    }

    @Test
    void deleteUserTest() {
        Mockito.when(userRepository.findByUsername("bob"))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class,
                () -> toDoListService.deleteUser("bob"));
    }

    @Test
    void updateUserTest() {
        Mockito.when(userRepository.findByUsername("bob"))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class,
                () -> toDoListService.updateUser("bob", USER_CONCISE_DTO));
        Mockito.when(userRepository.findByUsername("bobo"))
                .thenReturn(Optional.of(USER_ENTITY));
        Assertions.assertThrows(IllegalDataChangeException.class,
                () -> toDoListService.updateUser("bobo", USER_CONCISE_DTO));
    }

    @Test
    void getAllUsersTest() {
        Pageable pageable = Mockito.mock(Pageable.class);
        Page page = Mockito.mock(Page.class);
        Mockito.when(userRepository.findAll(pageable))
                .thenReturn(page)
                .thenReturn(page);
        Mockito.when(page.map(any()))
                .thenReturn(page)
                .thenReturn(page);
        Mockito.when(page.getContent())
                .thenReturn(List.of())
                .thenReturn(List.of(USER_DTO));
        Assertions.assertEquals(toDoListService.getAllUsers(pageable),
                ResponseEntity.noContent().build());
        Assertions.assertEquals(toDoListService.getAllUsers(pageable),
                new ResponseEntity<>(List.of(USER_DTO), HttpStatus.OK));
    }

    @Test
    void createTaskTest() {
        Mockito.when(modelMapper.map(any(), any()))
                .thenReturn(TASK_ENTITY)
                .thenReturn(TASK_ENTITY)
                .thenReturn(TASK_DTO);
        Mockito.when(userRepository.findByUsername("bob"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(USER_ENTITY));
        Assertions.assertThrows(UserNotFoundException.class,
                () -> toDoListService.createTask("bob", TASK_CONCISE_DTO));
        Assertions.assertEquals(toDoListService.createTask("bob", TASK_CONCISE_DTO),
                ResponseEntity.ok(TASK_DTO));

    }

    @Test
    void getTaskTest() {
        Mockito.when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(USER_ENTITY))
                .thenReturn(Optional.of(new UserEntity("bobo", "bob", "USER", List.of())))
                .thenReturn(Optional.of(USER_ENTITY));
        Mockito.when(taskRepository.findById(1L))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(TASK_ENTITY))
                .thenReturn(Optional.of(TASK_ENTITY));
        Mockito.when(modelMapper.map(any(), any()))
                .thenReturn(TASK_DTO);
        Assertions.assertThrows(UserNotFoundException.class,
                () -> toDoListService.getTask("bob", 1L));
        Assertions.assertThrows(TaskNotFoundException.class,
                () -> toDoListService.getTask("bob", 1L));
        Assertions.assertThrows(ResponseStatusException.class,
                () -> toDoListService.getTask("bobo", 1L));
        Assertions.assertEquals(ResponseEntity.ok(TASK_DTO), toDoListService.getTask("bob", 1L));
    }

    @Test
    void deleteTaskTest() {
        Mockito.when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(USER_ENTITY))
                .thenReturn(Optional.of(new UserEntity("bobo", "bob", "USER", List.of())));
        Mockito.when(taskRepository.findById(1L))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(TASK_ENTITY));
        Assertions.assertThrows(UserNotFoundException.class,
                () -> toDoListService.deleteTask("bob", 1L));
        Assertions.assertThrows(TaskNotFoundException.class,
                () -> toDoListService.deleteTask("bob", 1L));
        Assertions.assertThrows(ResponseStatusException.class,
                () -> toDoListService.getTask("bobo", 1L));
    }

    @Test
    void updateTaskTest() {
        Mockito.when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(USER_ENTITY))
                .thenReturn(Optional.of(new UserEntity("bobo", "bob", "USER", List.of())))
                .thenReturn(Optional.of(USER_ENTITY));
        Mockito.when(taskRepository.findById(1L))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(TASK_ENTITY))
                .thenReturn(Optional.of(TASK_ENTITY));
        Mockito.when(modelMapper.map(any(), any()))
                .thenReturn(TASK_DTO);
        Assertions.assertThrows(UserNotFoundException.class,
                () -> toDoListService.updateTask("bob", 1L, TASK_CONCISE_DTO));
        Assertions.assertThrows(TaskNotFoundException.class,
                () -> toDoListService.updateTask("bob", 1L, TASK_CONCISE_DTO));
        Assertions.assertThrows(ResponseStatusException.class,
                () -> toDoListService.updateTask("bobo", 1L, TASK_CONCISE_DTO));
        Assertions.assertEquals(ResponseEntity.ok(TASK_DTO),
                toDoListService.updateTask("bob", 1L, TASK_CONCISE_DTO));
    }

    @ParameterizedTest
    @MethodSource("statusesForPositiveTest")
    void updateTaskStatusPositiveTest(Status source, Status destination) throws NoSuchMethodException {
        Method method = ToDoListService.class.getDeclaredMethod("update", TaskEntity.class, TaskConciseDTO.class);
        method.setAccessible(true);
        TASK_ENTITY.setStatus(source);
        TASK_CONCISE_DTO.setStatus(destination);
        Assertions.assertDoesNotThrow(() -> method.invoke(toDoListService, TASK_ENTITY, TASK_CONCISE_DTO));

    }

    private static Stream<Arguments> statusesForPositiveTest() {
        return Stream.of(
                Arguments.of(Status.PLANNED, Status.PLANNED),
                Arguments.of(Status.PLANNED, Status.WORK_IN_PROGRESS),
                Arguments.of(Status.PLANNED, Status.POSTPONED),
                Arguments.of(Status.PLANNED, Status.NOTIFIED),
                Arguments.of(Status.PLANNED, Status.SIGNED),
                Arguments.of(Status.PLANNED, Status.DONE),
                Arguments.of(Status.PLANNED, Status.CANCELLED),
                Arguments.of(Status.WORK_IN_PROGRESS, Status.WORK_IN_PROGRESS),
                Arguments.of(Status.WORK_IN_PROGRESS, Status.POSTPONED),
                Arguments.of(Status.WORK_IN_PROGRESS, Status.NOTIFIED),
                Arguments.of(Status.WORK_IN_PROGRESS, Status.SIGNED),
                Arguments.of(Status.WORK_IN_PROGRESS, Status.DONE),
                Arguments.of(Status.WORK_IN_PROGRESS, Status.CANCELLED),
                Arguments.of(Status.POSTPONED, Status.WORK_IN_PROGRESS),
                Arguments.of(Status.POSTPONED, Status.POSTPONED),
                Arguments.of(Status.POSTPONED, Status.NOTIFIED),
                Arguments.of(Status.POSTPONED, Status.SIGNED),
                Arguments.of(Status.POSTPONED, Status.DONE),
                Arguments.of(Status.POSTPONED, Status.CANCELLED),
                Arguments.of(Status.NOTIFIED, Status.NOTIFIED),
                Arguments.of(Status.NOTIFIED, Status.SIGNED),
                Arguments.of(Status.NOTIFIED, Status.DONE),
                Arguments.of(Status.NOTIFIED, Status.CANCELLED),
                Arguments.of(Status.SIGNED, Status.NOTIFIED),
                Arguments.of(Status.SIGNED, Status.SIGNED),
                Arguments.of(Status.SIGNED, Status.DONE),
                Arguments.of(Status.SIGNED, Status.CANCELLED),
                Arguments.of(Status.DONE, Status.DONE),
                Arguments.of(Status.CANCELLED, Status.CANCELLED)
        );
    }

    @ParameterizedTest
    @MethodSource("statusesForNegativeTest")
    void updateTaskStatusNegativeTest(Status source, Status destination) throws NoSuchMethodException {
        Method method = ToDoListService.class.getDeclaredMethod("update", TaskEntity.class, TaskConciseDTO.class);
        method.setAccessible(true);
        TASK_ENTITY.setStatus(source);
        TASK_CONCISE_DTO.setStatus(destination);
        Assertions.assertThrows(InvocationTargetException.class,
                () -> method.invoke(toDoListService, TASK_ENTITY, TASK_CONCISE_DTO));

    }

    private static Stream<Arguments> statusesForNegativeTest() {
        return Stream.of(
                Arguments.of(Status.DONE, Status.PLANNED),
                Arguments.of(Status.DONE, Status.WORK_IN_PROGRESS),
                Arguments.of(Status.DONE, Status.POSTPONED),
                Arguments.of(Status.DONE, Status.NOTIFIED),
                Arguments.of(Status.DONE, Status.SIGNED),
                Arguments.of(Status.DONE, Status.CANCELLED),
                Arguments.of(Status.CANCELLED, Status.PLANNED),
                Arguments.of(Status.CANCELLED, Status.WORK_IN_PROGRESS),
                Arguments.of(Status.CANCELLED, Status.POSTPONED),
                Arguments.of(Status.CANCELLED, Status.NOTIFIED),
                Arguments.of(Status.CANCELLED, Status.SIGNED),
                Arguments.of(Status.CANCELLED, Status.DONE),
                Arguments.of(Status.SIGNED, Status.WORK_IN_PROGRESS),
                Arguments.of(Status.SIGNED, Status.POSTPONED),
                Arguments.of(Status.SIGNED, Status.PLANNED),
                Arguments.of(Status.NOTIFIED, Status.WORK_IN_PROGRESS),
                Arguments.of(Status.NOTIFIED, Status.POSTPONED),
                Arguments.of(Status.NOTIFIED, Status.PLANNED),
                Arguments.of(Status.POSTPONED, Status.PLANNED),
                Arguments.of(Status.WORK_IN_PROGRESS, Status.PLANNED)
        );
    }

    @Test
    void getAllTasksTest() {
        Pageable pageable = Mockito.mock(Pageable.class);
        Page page = Mockito.mock(Page.class);
        Mockito.when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(USER_ENTITY));
        Mockito.when(taskRepository.findAllByUserEntity(USER_ENTITY, pageable))
                .thenReturn(page)
                .thenReturn(page);
        Mockito.when(page.map(any()))
                .thenReturn(page)
                .thenReturn(page);
        Mockito.when(page.getContent())
                .thenReturn(List.of())
                .thenReturn(List.of(TASK_DTO));
        Assertions.assertThrows(UserNotFoundException.class,
                () -> toDoListService.getAllTasks("bob", pageable));
        Assertions.assertEquals(ResponseEntity.noContent().build(),
                toDoListService.getAllTasks("bob", pageable));
        Assertions.assertEquals(new ResponseEntity<>(List.of(TASK_DTO), HttpStatus.OK),
                toDoListService.getAllTasks("bob", pageable));
    }
}