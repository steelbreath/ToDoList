package ua.org.shpp.todolist.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserConciseDTO {
    @Size(min = 3, max = 20, message = "Username must contain from 3 to 20 symbols")
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String username;
    @Size(min = 3, max = 20, message = "Password must contain from 3 to 20 symbols")
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String password;

    public UserConciseDTO() {
    }

    public UserConciseDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
