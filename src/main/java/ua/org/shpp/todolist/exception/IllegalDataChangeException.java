package ua.org.shpp.todolist.exception;

import ua.org.shpp.todolist.enums.Status;

public class IllegalDataChangeException extends RuntimeException {

    private Status current;
    private Status toUpdate;

    public IllegalDataChangeException() {
    }

    public IllegalDataChangeException(String message) {
        super(message);
    }

    public IllegalDataChangeException(String message,Status current, Status toUpdate){
        super(message);
        this.current = current;
        this.toUpdate = toUpdate;
    }

    public Status getCurrent() {
        return current;
    }

    public Status getToUpdate() {
        return toUpdate;
    }
}
