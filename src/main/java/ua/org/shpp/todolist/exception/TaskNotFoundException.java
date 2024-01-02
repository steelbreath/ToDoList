package ua.org.shpp.todolist.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException() {
        super("Task with this id not found!");
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
