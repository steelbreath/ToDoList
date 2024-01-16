package ua.org.shpp.todolist.exception;

public class TaskNotFoundException extends RuntimeException{

    private Long id;
    public TaskNotFoundException() {}

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(Long id) {
        super("task.notfound.error");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
