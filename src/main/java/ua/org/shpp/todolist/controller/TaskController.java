package ua.org.shpp.todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.org.shpp.todolist.dto.TaskConciseDTO;
import ua.org.shpp.todolist.dto.TaskDTO;
import ua.org.shpp.todolist.exception.ErrorMessage;
import ua.org.shpp.todolist.service.TaskService;

import java.util.List;

@RestController
@RequestMapping(value = "/tasks", produces = "application/json")
@Tag(name = "Task operations", description = "Realizes crud operations.")
@ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = TaskDTO.class), mediaType = "application/json")})
@ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema())})
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(
            summary = "Create task and add to database",
            description = "Create Task object specifying status, description and deadline. The response is TaskDTO object with id, status, description, created at and deadline.")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskConciseDTO task){
        return taskService.createTask(task);
    }

    @Operation(
            summary = "Retrieve task by id",
            description = "Get Task object by specifying its id. The response is TaskDTO object with id, status, description, created at and deadline.")
    @ApiResponse(responseCode = "404", description = "Task Not Found", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long id){
        return taskService.getTask(id);
    }

    @Operation(
            summary = "Delete task by id",
            description = "Delete Task object by specifying its id. The response is OK if Task was deleted and 400 if not.")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }

    @Operation(
            summary = "Update task by id",
            description = "Update Task object specifying status, description and deadline. The response is TaskDTO object with id, status, description, created at and deadline.")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", description = "Task Not Found", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskConciseDTO task){
        return taskService.updateTask(id,task);
    }

    @Operation(
            summary = "Retrieve all tasks",
            description = "Retrieve all Task from database. You can specify quantity of tasks on one page and sort them by any parameter. The response is List of TaskDTO objects with id, status, description, created at and deadline.")
    @ApiResponse(responseCode = "204", description = "No Content", content = {@Content(schema = @Schema())})
    @GetMapping()
    public ResponseEntity<List<TaskDTO>> getAllTask(@ParameterObject Pageable pageable){
        return taskService.getAllTask(pageable);
    }
}
