package ua.org.shpp.todolist.exception;

public class UserNotFoundException extends RuntimeException {

    private String username;

    public UserNotFoundException() {
    }

    public UserNotFoundException(String username) {
        super("user.notfound.error");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
