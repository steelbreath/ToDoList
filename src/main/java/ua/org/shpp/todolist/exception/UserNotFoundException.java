package ua.org.shpp.todolist.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User with this username not found!");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
