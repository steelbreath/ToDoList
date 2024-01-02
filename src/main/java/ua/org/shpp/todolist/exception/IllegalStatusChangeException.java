package ua.org.shpp.todolist.exception;

public class IllegalStatusChangeException extends RuntimeException {
    public IllegalStatusChangeException() {
    }

    public IllegalStatusChangeException(String message) {
        super(message);
    }
}
