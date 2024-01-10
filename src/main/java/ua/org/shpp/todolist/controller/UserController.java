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
import ua.org.shpp.todolist.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
@SecurityRequirement(name = "security")
@Tag(name = "User operations", description = "Realizes crud operations with User object.")
@ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json")})
@ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema())})
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Sign up user",
            description = "Create User object specifying username and password. The response is UserDTO object with id, username and roles.")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @PostMapping("/sign-up")
    public ResponseEntity<UserDTO> createTask(@RequestBody UserConciseDTO user){
        return userService.signUp(user);
    }

    @Operation(
            summary = "Retrieve user by id",
            description = "Get User object by specifying its id. The response is UserDTO object with id, username and roles.")
    @ApiResponse(responseCode = "404", description = "User Not Found", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getTask(@PathVariable Long id){
        return userService.getUser(id);
    }

    @Operation(
            summary = "Delete user by id",
            description = "Delete User object by specifying its id. The response is OK if User was deleted and 400 if not.")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @Operation(
            summary = "Update user by id",
            description = "Update User object specifying username and password. The response is UserDTO object with id, username and roles.")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", description = "User Not Found", content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateTask(@PathVariable Long id, @RequestBody UserConciseDTO user){
        return userService.updateUser(id,user);
    }

    @Operation(
            summary = "Retrieve all users",
            description = "Retrieve all Users from database. You can specify quantity of users on one page and sort them by any parameter. The response is List of userDTO objects with id, username and roles.")
    @ApiResponse(responseCode = "204", description = "No Content", content = {@Content(schema = @Schema())})
    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers(@ParameterObject Pageable pageable){
        return userService.getAllUsers(pageable);
    }
}
