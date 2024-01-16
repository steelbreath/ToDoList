package ua.org.shpp.todolist.exception;

public class UsernameAlreadyExistException extends RuntimeException {

    private String username;
    public UsernameAlreadyExistException() {
    }

    public UsernameAlreadyExistException(String message) {
        super(message);
    }

    public UsernameAlreadyExistException(String message, String username) {
        super(message);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
