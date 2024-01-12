package ua.org.shpp.todolist.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException() {
        super("task.notfound.error");
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
