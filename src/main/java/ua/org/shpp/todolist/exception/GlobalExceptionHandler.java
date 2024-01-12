package ua.org.shpp.todolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({TaskNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ProblemDetail> handleTaskNotFoundException(TaskNotFoundException ex, WebRequest request) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        detail.setInstance(URI.create(request.getDescription(false)));
        detail.setProperty("timestamp",LocalDateTime.now());
        return ResponseEntity.of(detail).build();
    }

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ProblemDetail> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        detail.setInstance(URI.create(request.getDescription(false)));
        detail.setProperty("timestamp",LocalDateTime.now());
        return ResponseEntity.of(detail).build();
    }

    @ExceptionHandler({IllegalDataChangeException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ProblemDetail> handleIllegalStatusChangeException(IllegalDataChangeException ex, WebRequest request) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        detail.setInstance(URI.create(request.getDescription(false)));
        detail.setProperty("timestamp",LocalDateTime.now());
        return ResponseEntity.of(detail).build();
    }

    @ExceptionHandler({UsernameAlreadyExistException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ProblemDetail> handleUsernameAlreadyExistException(UsernameAlreadyExistException ex, WebRequest request) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        detail.setInstance(URI.create(request.getDescription(false)));
        detail.setProperty("timestamp",LocalDateTime.now());
        return ResponseEntity.of(detail).build();
    }
}
