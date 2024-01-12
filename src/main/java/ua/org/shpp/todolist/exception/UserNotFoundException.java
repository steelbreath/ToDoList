package ua.org.shpp.todolist.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("user.notfound.error");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
