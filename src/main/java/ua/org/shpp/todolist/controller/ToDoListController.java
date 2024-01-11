package ua.org.shpp.todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.org.shpp.todolist.dto.TaskConciseDTO;
import ua.org.shpp.todolist.dto.TaskDTO;
import ua.org.shpp.todolist.dto.UserConciseDTO;
import ua.org.shpp.todolist.dto.UserDTO;
import ua.org.shpp.todolist.exception.ErrorMessage;
import ua.org.shpp.todolist.service.ToDoListService;

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
@SecurityRequirement(name = "security")
@Tag(name = "ToDoList Controller", description = "Realizes crud operations with User and their tasks object.")
@ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json")})
@ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema())})
public class ToDoListController {
    private final ToDoListService toDoListService;

    @Autowired
    public ToDoListController(ToDoListService toDoListService) {
        this.toDoListService = toDoListService;
    }

    @Operation(
            summary = "Sign up user",
            description = "Create User object specifying username and password. The response is UserDTO object with id, username and roles.")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @PostMapping("/sign-up")
    public ResponseEntity<UserDTO> createTask(@RequestBody UserConciseDTO user) {
        return toDoListService.signUp(user);
    }

    @Operation(
            summary = "Retrieve user by username",
            description = "Get User object by specifying its username. The response is UserDTO object with username and roles.")
    @ApiResponse(responseCode = "404", description = "User Not Found", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getTask(@PathVariable String username) {
        return toDoListService.getUser(username);
    }

    @Operation(
            summary = "Delete user by username",
            description = "Delete User object by specifying its username. The response is OK if User was deleted and 400 if not.")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @DeleteMapping("/{username}")
    public void deleteTask(@PathVariable String username) {
        toDoListService.deleteUser(username);
    }

    @Operation(
            summary = "Update user",
            description = "Update User object specifying username and password. The response is UserDTO object with username and roles.")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", description = "User Not Found", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @PutMapping("/{username}")
    public ResponseEntity<UserDTO> updateTask(@PathVariable String username, @RequestBody UserConciseDTO user) {
        return toDoListService.updateUser(username, user);
    }

    @Operation(
            summary = "Retrieve all users",
            description = "Retrieve all Users from database. You can specify quantity of users on one page and sort them by any parameter. The response is List of userDTO objects with id, username and roles.")
    @ApiResponse(responseCode = "204", description = "No Content", content = {@Content(schema = @Schema())})
    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers(@ParameterObject Pageable pageable) {
        return toDoListService.getAllUsers(pageable);
    }

    @Operation(
            summary = "Create task",
            description = "Create Task object specifying status, description and deadline. The response is TaskDTO object with id, status, description, created at and deadline.")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @PostMapping("/{username}/tasks")
    public ResponseEntity<TaskDTO> createTask(@PathVariable String username, @RequestBody TaskConciseDTO task) {
        return toDoListService.createTask(username, task);
    }

    @Operation(
            summary = "Retrieve task by id",
            description = "Get Task object by specifying its id. The response is TaskDTO object with id, status, description, created at and deadline.")
    @ApiResponse(responseCode = "404", description = "Task Not Found", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @GetMapping("/{username}/tasks/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable String username, @PathVariable Long id) {
        return toDoListService.getTask(username, id);
    }

    @Operation(
            summary = "Delete task by id",
            description = "Delete Task object by specifying its id. The response is OK if Task was deleted and 400 if not.")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @DeleteMapping("/{username}/tasks/{id}")
    public void deleteTask(@PathVariable String username, @PathVariable Long id) {
        toDoListService.deleteTask(username, id);
    }

    @Operation(
            summary = "Update task by id",
            description = "Update Task object specifying status, description and deadline. The response is TaskDTO object with id, status, description, created at and deadline.")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", description = "Task Not Found", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @PutMapping("/{username}/tasks/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable String username, @PathVariable Long id, @RequestBody TaskConciseDTO task) {
        return toDoListService.updateTask(username, id, task);
    }

    @Operation(
            summary = "Retrieve all tasks",
            description = "Retrieve all Task from database. You can specify quantity of tasks on one page and sort them by any parameter. The response is List of TaskDTO objects with id, status, description, created at and deadline.")
    @ApiResponse(responseCode = "204", description = "No Content", content = {@Content(schema = @Schema())})
    @GetMapping("/{username}/tasks")
    public ResponseEntity<List<TaskDTO>> getAllTasks(@PathVariable String username, @ParameterObject Pageable pageable) {
        return toDoListService.getAllTasks(username, pageable);
    }
}
