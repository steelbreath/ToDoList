package ua.org.shpp.todolist.exception;

public class IllegalDataChangeException extends RuntimeException {
    public IllegalDataChangeException() {
    }

    public IllegalDataChangeException(String message) {
        super(message);
    }
}
