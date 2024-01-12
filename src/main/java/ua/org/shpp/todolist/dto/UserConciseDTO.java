package ua.org.shpp.todolist.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserConciseDTO {
    @Size(min = 3, max = 20, message = "{user.invalid.size.username}")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "{user.invalid.pattern.username}")
    private String username;
    @Size(min = 3, max = 20, message = "{user.invalid.size.password}")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "{user.invalid.pattern.password}")
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
